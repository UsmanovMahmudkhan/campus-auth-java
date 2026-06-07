# Changelog

All notable changes to `campus-auth-java` are documented here. The format is
based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/), and this
project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## 0.1.0 - 2026-06-07

Initial public release. This release reworks the project into a safe,
demo-first JVM package suitable for Maven Central. Earlier pre-public iterations
that targeted real campus login systems were removed; see the notes below.

### Added

- Public API: `CampusAuthClient`, `AuthProvider`, `AuthRequest`, `AuthResult`,
  `CampusMember`, `CampusRole`, `CampusAuthException`,
  `InvalidCredentialsException`, `ProviderUnavailableException`.
- `DemoAuthProvider`: a safe, fully in-memory provider with a default
  `demo-student` / `demo-password` account and constant-time comparison.
- A minimal CLI (`verify --provider demo --id <id>`) that reads the password
  without echo and never prints or logs it.
- JUnit 5 test suite covering success, failure, validation, exceptions, the
  public API, and the CLI.
- Maven Central publishing setup: source jar, javadoc jar, GPG signing, and the
  Sonatype `central-publishing-maven-plugin` (in a `release` profile).
- GitHub Actions for CI (`mvn -B clean verify`) and manual release.

### Changed

- Coordinates are now `io.github.usmanovmahmudkhan:campus-auth-java:0.1.0`.
- Java baseline raised to 17.
- Package renamed to `io.github.usmanovmahmudkhan.campusauth`.

### Removed

- All real-system authenticators and scrapers (Portal SSO, Classic, Moodler,
  DoSejong) and the `LegacyAuth` helpers. They targeted live third-party
  endpoints and parsed authenticated session pages, which is unsafe to ship and
  out of scope for a demo package. Real integrations must be implemented
  privately by authorized applications via the `AuthProvider` interface.
- The `jsoup` dependency, which was only used by the removed scrapers.
- The marketing documentation website that described those authenticators.
