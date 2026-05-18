# Release Notes

## campus-auth-java 0.2.7

This release is the corrected latest public release for `campus-auth-java`.

### Highlights

- Java 11 Maven project with a runnable shaded jar.
- CLI support for automatic fallback, interactive passwords, and selected authenticators.
- Library APIs through `AuthService`, `AuthMethod`, `AuthResult`, and `AuthResponse`.
- Authenticator strategies for Portal SSO, Classic Session, Moodler Session, and DoSejong Session.
- Structured JSON output for success state, authentication state, status code, result code, metadata, and authenticator name.
- Multi-page documentation website deployed with Netlify.
- Community health files for contribution, support, security, roadmap, and conduct.

### Build

```bash
mvn clean package
```

### Run

```bash
java -jar target/campus-auth-java-0.2.7.jar <student_id> <password>
```

## Release History

- `v0.1.0` initial Java toolkit.
- `v0.1.1` CLI and JSON output polish.
- `v0.2.0` multiple authenticator support.
- `v0.2.6` documentation and disclaimer preparation.
- `v0.2.7` polished latest release.

## Disclaimer

This project is provided strictly for educational, research, and authorized testing purposes only.

It must not be used for illegal activities, unauthorized access, privacy violations, abuse, harassment, disruption of services, or any activity that violates applicable laws, platform rules, or third-party rights.

The developer does not encourage, support, or take responsibility for any misuse of this project. Users are solely responsible for how they use, modify, or distribute this code. By using this project, you agree that you are responsible for ensuring your actions are legal, ethical, and authorized.

If you are unsure whether your use is allowed, do not use this project.
