# campus-auth-java

A small, demo-first JVM toolkit that models campus-style member verification
workflows. It gives applications a clean API and a simple CLI for verifying a
member id and secret against a pluggable provider, and ships one safe,
fully in-memory provider out of the box.

> **Disclaimer**
>
> This project is not affiliated with, endorsed by, or officially connected to
> Sejong University. It is an educational/demo JVM package for campus-style
> authentication and member-verification workflows. Do not use it to automate,
> bypass, attack, or abuse real university authentication systems.

## Installation

### Maven

```xml
<dependency>
  <groupId>io.github.usmanovmahmudkhan</groupId>
  <artifactId>campus-auth-java</artifactId>
  <version>0.1.0</version>
</dependency>
```

### Gradle

```kotlin
implementation("io.github.usmanovmahmudkhan:campus-auth-java:0.1.0")
```

Requires Java 17 or newer.

## Quick start

```java
import io.github.usmanovmahmudkhan.campusauth.*;

CampusAuthClient client = CampusAuthClient.withProvider(new DemoAuthProvider());

AuthResult result = client.verify(
    AuthRequest.of("demo-student", "demo-password")
);

if (result.isAuthenticated()) {
    System.out.println(result.member().id());
}
```

The bundled `DemoAuthProvider` knows one account by default:

| id             | password        | role    |
| -------------- | --------------- | ------- |
| `demo-student` | `demo-password` | STUDENT |

Register more demo accounts as needed:

```java
DemoAuthProvider provider = new DemoAuthProvider()
    .withAccount("demo-faculty", "demo-secret",
        CampusMember.of("demo-faculty", "Demo Faculty", CampusRole.FACULTY));
```

## CLI usage

The build produces a runnable jar with a single `verify` command:

```bash
java -jar campus-auth-java-0.1.0.jar verify --provider demo --id demo-student
```

You will be prompted for the password without echo. You may also pass
`--password <secret>` for non-interactive use (for example in tests). The CLI
never prints or logs the secret.

```
Authenticated: demo-student (STUDENT)
```

Exit codes: `0` authenticated, `1` not authenticated, `2` usage or input error.

## API overview

| Type                           | Purpose                                                      |
| ------------------------------ | ----------------------------------------------------------- |
| `CampusAuthClient`             | Entry point; wraps a provider and exposes `verify`.         |
| `AuthProvider`                 | Strategy interface for verifying a request.                 |
| `AuthRequest`                  | Immutable id + secret; the secret can be cleared.           |
| `AuthResult`                   | Outcome of a verification (authenticated or failed).        |
| `CampusMember`                 | Non-sensitive identity metadata for a verified member.      |
| `CampusRole`                   | `STUDENT`, `FACULTY`, `STAFF`, `GUEST`.                     |
| `DemoAuthProvider`             | Safe, in-memory provider used by the examples and tests.    |
| `CampusAuthException`          | Base unchecked exception.                                   |
| `InvalidCredentialsException`  | Raised when a verified member is read from a failed result. |
| `ProviderUnavailableException` | Raised when a provider cannot service the request.          |

`verify` returns a failed `AuthResult` for well-formed-but-incorrect
credentials (no exception). Calling `result.member()` on a failed result throws
`InvalidCredentialsException`, so guard it with `isAuthenticated()` as shown
above. Null/blank ids or secrets are rejected by `AuthRequest.of` with
`IllegalArgumentException`.

## Demo provider

`DemoAuthProvider` is the default and only bundled provider. It is entirely
in-memory, performs constant-time password comparison, and talks to no external
service. It exists so the API, examples, and tests run anywhere without
credentials or network access. Do not place real credentials in it.

To integrate a real member directory, implement `AuthProvider` yourself inside
the application that owns and is authorized to use that system. This package
intentionally ships no real-system integration. See [SECURITY.md](SECURITY.md)
for the permission-only integration policy.

## Security notes

- No real credentials, student ids, passwords, tokens, cookies, or session
  material are stored in this repository.
- `AuthRequest` holds the secret as a `char[]` and supports `clear()`.
- The CLI reads the password without echo and never prints or logs it.
- See [SECURITY.md](SECURITY.md) for reporting and integration policy.

## What this project is not

- It is not a login client for any real university or service.
- It does not scrape, brute force, bypass, or automate any real authentication
  system.
- It is not affiliated with or endorsed by Sejong University or any institution.
- It is not a credential store and must not be used to hold real secrets.

## Maven Central publishing status

Published. `io.github.usmanovmahmudkhan:campus-auth-java:0.1.0` is available on
[Maven Central](https://central.sonatype.com/artifact/io.github.usmanovmahmudkhan/campus-auth-java/0.1.0).
Publishing steps are documented in [CONTRIBUTING.md](CONTRIBUTING.md).

## License

Released under the [MIT License](LICENSE).
