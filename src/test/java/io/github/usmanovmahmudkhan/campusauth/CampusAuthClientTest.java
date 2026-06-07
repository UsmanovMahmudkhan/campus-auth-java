package io.github.usmanovmahmudkhan.campusauth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CampusAuthClientTest {

    private final CampusAuthClient client =
            CampusAuthClient.withProvider(new DemoAuthProvider());

    @Test
    void verifiesDefaultDemoAccount() {
        AuthResult result = client.verify(
                AuthRequest.of(DemoAuthProvider.DEFAULT_ID, DemoAuthProvider.DEFAULT_PASSWORD));

        assertTrue(result.isAuthenticated());
        assertEquals("demo-student", result.member().id());
        assertEquals(CampusRole.STUDENT, result.member().role());
    }

    @Test
    void readmeQuickStartCompilesAndRuns() {
        CampusAuthClient client = CampusAuthClient.withProvider(new DemoAuthProvider());
        AuthResult result = client.verify(AuthRequest.of("demo-student", "demo-password"));
        assertTrue(result.isAuthenticated());
        assertEquals("demo-student", result.member().id());
    }

    @Test
    void failsOnWrongPassword() {
        AuthResult result = client.verify(AuthRequest.of("demo-student", "wrong-password"));

        assertFalse(result.isAuthenticated());
        assertEquals("invalid id or password", result.reason());
    }

    @Test
    void failsOnUnknownId() {
        AuthResult result = client.verify(AuthRequest.of("nobody", "demo-password"));
        assertFalse(result.isAuthenticated());
    }

    @Test
    void memberOnFailedResultThrowsInvalidCredentials() {
        AuthResult result = client.verify(AuthRequest.of("demo-student", "wrong-password"));
        assertThrows(InvalidCredentialsException.class, result::member);
    }

    @Test
    void unavailableProviderRaisesProviderUnavailable() {
        CampusAuthClient unavailable =
                CampusAuthClient.withProvider(DemoAuthProvider.unavailable());
        assertThrows(ProviderUnavailableException.class,
                () -> unavailable.verify(AuthRequest.of("demo-student", "demo-password")));
    }

    @Test
    void invalidCredentialsIsACampusAuthException() {
        assertTrue(CampusAuthException.class.isAssignableFrom(InvalidCredentialsException.class));
        assertTrue(CampusAuthException.class.isAssignableFrom(ProviderUnavailableException.class));
    }

    @Test
    void rejectsNullProvider() {
        assertThrows(NullPointerException.class, () -> CampusAuthClient.withProvider(null));
    }

    @Test
    void verifiesAdditionalRegisteredAccount() {
        DemoAuthProvider provider = new DemoAuthProvider()
                .withAccount("demo-faculty", "demo-secret",
                        CampusMember.of("demo-faculty", "Demo Faculty", CampusRole.FACULTY));
        AuthResult result = CampusAuthClient.withProvider(provider)
                .verify(AuthRequest.of("demo-faculty", "demo-secret"));

        assertTrue(result.isAuthenticated());
        assertEquals(CampusRole.FACULTY, result.member().role());
    }
}
