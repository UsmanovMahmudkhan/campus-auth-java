# Changelog

All notable changes to `campus-auth-java` are documented here.

## 0.3.4 - 2026-05-18

- Added a runnable Maven Java project with Java 11 support.
- Added a CLI entry point that returns structured JSON authentication results.
- Added reusable library APIs through `AuthService`, `AuthMethod`, `AuthResult`, and `AuthResponse`.
- Added authenticator implementations for portal SSO, classic session, Moodler session, and Do Sejong session flows.
- Added session cookie handling, form-encoded HTTP requests, redirects, and timeout-safe error responses.
- Added compatibility helper methods in `LegacyAuth`.
- Added `.gitignore` coverage for Java, Maven, IDE files, environment files, and build output.
- Added project documentation, release notes, and license metadata.
