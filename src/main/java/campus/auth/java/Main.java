package campus.auth.java;

import java.io.Console;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        if (args.length < 1 || args.length > 3) {
            System.err.println("Usage: java -jar campus-auth-java-0.3.4.jar <id> [password] [method]");
            System.err.println("Methods: Manual, PortalSSOToken, ClassicSession, MoodlerSession, DosejongSession");
            System.exit(2);
        }

        String id = args[0];
        String password = args.length >= 2 ? args[1] : readPassword();
        String methodArg = args.length == 3 ? args[2] : "Manual";

        if ("Manual".equalsIgnoreCase(methodArg)) {
            System.out.println(AuthService.authenticate(id, password).toJson());
            return;
        }

        if (methodArg.contains(",")) {
            List<AuthMethod> methods = Arrays.stream(methodArg.split(","))
                    .map(String::trim)
                    .filter(value -> !value.isEmpty())
                    .map(AuthMethod::fromName)
                    .collect(Collectors.toList());
            System.out.println(AuthService.authenticate(id, password, methods).toJson());
            return;
        }

        System.out.println(AuthService.authenticate(id, password, AuthMethod.fromName(methodArg)).toJson());
    }

    private static String readPassword() {
        Console console = System.console();
        if (console != null) {
            char[] password = console.readPassword("Password: ");
            return new String(password);
        }
        System.err.print("Password: ");
        return new Scanner(System.in).nextLine();
    }
}
