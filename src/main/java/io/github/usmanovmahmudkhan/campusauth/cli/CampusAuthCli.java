package io.github.usmanovmahmudkhan.campusauth.cli;

import io.github.usmanovmahmudkhan.campusauth.AuthRequest;
import io.github.usmanovmahmudkhan.campusauth.AuthResult;
import io.github.usmanovmahmudkhan.campusauth.CampusAuthClient;
import io.github.usmanovmahmudkhan.campusauth.CampusAuthException;
import io.github.usmanovmahmudkhan.campusauth.DemoAuthProvider;

import java.io.Console;
import java.io.PrintStream;
import java.util.Arrays;

/**
 * Minimal command-line front end.
 *
 * <p>Usage:
 * <pre>
 * verify --provider demo --id demo-student [--password &lt;secret&gt;]
 * </pre>
 *
 * <p>Only the in-memory {@code demo} provider is available. The secret is read
 * interactively (without echo) when {@code --password} is omitted, and is never
 * printed or logged.
 */
public final class CampusAuthCli {

    private final PrintStream out;
    private final PrintStream err;
    private final Console console;

    CampusAuthCli(PrintStream out, PrintStream err, Console console) {
        this.out = out;
        this.err = err;
        this.console = console;
    }

    /**
     * Program entry point.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        CampusAuthCli cli = new CampusAuthCli(System.out, System.err, System.console());
        System.exit(cli.run(args));
    }

    int run(String[] args) {
        if (args.length == 0 || isHelp(args[0])) {
            printUsage();
            return args.length == 0 ? 2 : 0;
        }
        if (!"verify".equals(args[0])) {
            err.println("Unknown command: " + args[0]);
            printUsage();
            return 2;
        }

        String provider = "demo";
        String id = null;
        char[] password = null;
        for (int i = 1; i < args.length; i++) {
            switch (args[i]) {
                case "--provider":
                    provider = value(args, ++i);
                    break;
                case "--id":
                    id = value(args, ++i);
                    break;
                case "--password":
                    String raw = value(args, ++i);
                    password = raw == null ? null : raw.toCharArray();
                    break;
                default:
                    err.println("Unknown option: " + args[i]);
                    printUsage();
                    return 2;
            }
        }

        if (!"demo".equalsIgnoreCase(provider)) {
            err.println("Unknown provider: " + provider + " (only 'demo' is available)");
            return 2;
        }
        if (id == null || id.isBlank()) {
            err.println("Missing required option: --id");
            printUsage();
            return 2;
        }
        if (password == null) {
            password = readPassword();
        }
        if (password == null || password.length == 0) {
            err.println("A password is required.");
            return 2;
        }

        try {
            AuthRequest request = AuthRequest.of(id, password);
            CampusAuthClient client = CampusAuthClient.withProvider(new DemoAuthProvider());
            AuthResult result = client.verify(request);
            request.clear();
            if (result.isAuthenticated()) {
                out.println("Authenticated: " + result.member().id()
                        + " (" + result.member().role() + ")");
                return 0;
            }
            out.println("Not authenticated: " + result.reason());
            return 1;
        } catch (IllegalArgumentException | CampusAuthException e) {
            err.println("Error: " + e.getMessage());
            return 2;
        } finally {
            Arrays.fill(password, '\0');
        }
    }

    private char[] readPassword() {
        if (console != null) {
            return console.readPassword("Password: ");
        }
        err.println("No interactive console available; pass --password.");
        return null;
    }

    private static boolean isHelp(String arg) {
        return "-h".equals(arg) || "--help".equals(arg) || "help".equals(arg);
    }

    private static String value(String[] args, int index) {
        return index < args.length ? args[index] : null;
    }

    private void printUsage() {
        out.println("campus-auth-java");
        out.println();
        out.println("Usage:");
        out.println("  verify --provider demo --id <id> [--password <secret>]");
        out.println();
        out.println("Notes:");
        out.println("  Only the in-memory 'demo' provider is available.");
        out.println("  Demo account: id 'demo-student', password 'demo-password'.");
        out.println("  Omit --password to be prompted without echo.");
    }
}
