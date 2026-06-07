package io.github.usmanovmahmudkhan.campusauth.cli;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CampusAuthCliTest {

    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final ByteArrayOutputStream err = new ByteArrayOutputStream();
    private final CampusAuthCli cli = new CampusAuthCli(
            new PrintStream(out, true, StandardCharsets.UTF_8),
            new PrintStream(err, true, StandardCharsets.UTF_8),
            null);

    private String out() {
        return out.toString(StandardCharsets.UTF_8);
    }

    private String err() {
        return err.toString(StandardCharsets.UTF_8);
    }

    @Test
    void verifiesDemoAccount() {
        int code = cli.run(new String[] {
                "verify", "--provider", "demo", "--id", "demo-student", "--password", "demo-password"});

        assertEquals(0, code);
        assertTrue(out().contains("Authenticated: demo-student"));
    }

    @Test
    void reportsFailedAuthentication() {
        int code = cli.run(new String[] {
                "verify", "--provider", "demo", "--id", "demo-student", "--password", "wrong"});

        assertEquals(1, code);
        assertTrue(out().contains("Not authenticated"));
    }

    @Test
    void neverPrintsThePassword() {
        cli.run(new String[] {
                "verify", "--provider", "demo", "--id", "demo-student", "--password", "demo-password"});

        assertFalse(out().contains("demo-password"));
        assertFalse(err().contains("demo-password"));
    }

    @Test
    void rejectsUnknownProvider() {
        int code = cli.run(new String[] {
                "verify", "--provider", "portal", "--id", "demo-student", "--password", "x"});

        assertEquals(2, code);
        assertTrue(err().contains("Unknown provider"));
    }

    @Test
    void requiresId() {
        int code = cli.run(new String[] {"verify", "--provider", "demo", "--password", "x"});
        assertEquals(2, code);
        assertTrue(err().contains("--id"));
    }

    @Test
    void noArgsPrintsUsage() {
        int code = cli.run(new String[] {});
        assertEquals(2, code);
        assertTrue(out().contains("Usage"));
    }

    @Test
    void helpExitsZero() {
        int code = cli.run(new String[] {"--help"});
        assertEquals(0, code);
    }
}
