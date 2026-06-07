package io.github.usmanovmahmudkhan.campusauth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AuthRequestTest {

    @Test
    void rejectsNullId() {
        assertThrows(IllegalArgumentException.class, () -> AuthRequest.of(null, "pw"));
    }

    @Test
    void rejectsBlankId() {
        assertThrows(IllegalArgumentException.class, () -> AuthRequest.of("   ", "pw"));
    }

    @Test
    void rejectsNullPassword() {
        assertThrows(IllegalArgumentException.class, () -> AuthRequest.of("id", (String) null));
    }

    @Test
    void rejectsBlankPassword() {
        assertThrows(IllegalArgumentException.class, () -> AuthRequest.of("id", ""));
    }

    @Test
    void copiesPasswordDefensively() {
        char[] secret = {'a', 'b', 'c'};
        AuthRequest request = AuthRequest.of("id", secret);
        secret[0] = 'z';
        assertArrayEquals(new char[] {'a', 'b', 'c'}, request.password());
    }

    @Test
    void clearZeroesTheSecret() {
        AuthRequest request = AuthRequest.of("id", "secret");
        request.clear();
        assertArrayEquals(new char[6], request.password());
    }

    @Test
    void toStringHidesPassword() {
        AuthRequest request = AuthRequest.of("alice", "topsecret");
        assertFalse(request.toString().contains("topsecret"));
        assertEquals("AuthRequest{id='alice', password=***}", request.toString());
    }
}
