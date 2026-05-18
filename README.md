<div align="center">
  <a href="https://commons.wikimedia.org/wiki/File:Sejong_univ.png">
    <img src="https://upload.wikimedia.org/wikipedia/commons/0/0f/Sejong_univ.png" alt="Sejong University logo" width="104" />
  </a>
  <h1>Campus Auth Java</h1>
  <p><b>A lightweight Java library and CLI for campus member account authentication workflows.</b></p>

  <p>
    <img src="https://img.shields.io/badge/Java-11%2B-blue?logo=openjdk&logoColor=white" alt="Java 11+" />
    <img src="https://img.shields.io/badge/Maven-3.8%2B-red?logo=apachemaven&logoColor=white" alt="Maven 3.8+" />
    <img src="https://img.shields.io/badge/Release-v0.2.7-0f766e" alt="Release v0.2.7" />
    <img src="https://img.shields.io/badge/License-MIT-green.svg" alt="License: MIT" />
  </p>

  <p>
    <a href="https://campus-auth-java.netlify.app">Documentation</a>
    ·
    <a href="https://campus-auth-java.netlify.app/quickstart/">Quickstart</a>
    ·
    <a href="https://github.com/UsmanovMahmudkhan/campus-auth-java/releases/tag/v0.2.7">Latest release</a>
  </p>
</div>

> This project is not affiliated with, endorsed by, or officially maintained by Sejong University.

## Table of Contents

- [About](#about)
- [Features](#features)
- [Quickstart](#quickstart)
- [CLI Usage](#cli-usage)
- [Java API](#java-api)
- [Authentication Methods](#authentication-methods)
- [Security and Disclaimer](#security-and-disclaimer)
- [Documentation](#documentation)
- [Contributing](#contributing)

## About

**Campus Auth Java** wraps session-based campus login checks behind a small Java 11 toolkit. It handles HTTP requests, cookies, redirects, HTML parsing, fallback authentication methods, and structured JSON output so applications can make clear decisions from authentication results.

Use it when you need a local CLI or Java API for authorized campus-account verification experiments, club tools, or research prototypes.

## Features

- **Multiple authentication strategies:** Portal SSO, Classic Session, Moodler Session, and DoSejong Session.
- **Manual fallback mode:** Try supported methods in order until a definite result is found.
- **Structured JSON output:** Consistent success, status, result code, authenticator, and metadata fields.
- **Library and CLI support:** Use from Java code or package a runnable shaded jar.
- **Small dependency surface:** Java `HttpClient` plus JSoup for HTML parsing.
- **Extensible design:** Add new authenticators behind the shared `Authenticator` contract.

## Quickstart

### Prerequisites

- JDK 11 or newer
- Maven 3.8 or newer

### Build

```bash
git clone https://github.com/UsmanovMahmudkhan/campus-auth-java.git
cd campus-auth-java
mvn clean package
```

The shaded jar is generated at:

```text
target/campus-auth-java-0.2.7.jar
```

## CLI Usage

Run automatic fallback mode:

```bash
java -jar target/campus-auth-java-0.2.7.jar <student_id> <password>
```

Use the interactive password prompt:

```bash
java -jar target/campus-auth-java-0.2.7.jar <student_id>
```

Run one or more selected authenticators:

```bash
java -jar target/campus-auth-java-0.2.7.jar <student_id> <password> PortalSSOToken
java -jar target/campus-auth-java-0.2.7.jar <student_id> <password> PortalSSOToken,DosejongSession
```

## Java API

```java
import campus.auth.java.AuthMethod;
import campus.auth.java.AuthResponse;
import campus.auth.java.AuthResult;
import campus.auth.java.AuthService;

public class App {
    public static void main(String[] args) {
        AuthResult fallback = AuthService.authenticate("student_id", "password");
        System.out.println(fallback.toJson());

        AuthResponse portal = AuthService.authenticate(
            "student_id",
            "password",
            AuthMethod.PORTAL_SSO_TOKEN
        );
        System.out.println(portal.getIsAuth());
    }
}
```

## Authentication Methods

| Method | Purpose | Extracted Metadata |
| :--- | :--- | :--- |
| `Manual` | Cascades through supported methods. | Varies by successful method |
| `PortalSSOToken` | Checks portal SSO token behavior via Blackboard. | Authentication status |
| `ClassicSession` | Checks Daeyang Humanity College session flow. | Classic reading certification |
| `MoodlerSession` | Checks SJULMS Moodler session login. | Name, major |
| `DosejongSession` | Checks Do Sejong session login. | Name, major |

Legacy helper methods remain available in `LegacyAuth` for backward compatibility: `dosejongApi`, `uisApi`, and `sjlmsApi`.

## Security and Disclaimer

Credentials are passed at runtime through CLI arguments, an interactive prompt, or Java method calls. Never hardcode real credentials, commit `.env` files, or use the project against accounts or services you are not authorized to test.

This project is provided strictly for educational, research, and authorized testing purposes only.

It must not be used for illegal activities, unauthorized access, privacy violations, abuse, harassment, disruption of services, or any activity that violates applicable laws, platform rules, or third-party rights.

The developer does not encourage, support, or take responsibility for any misuse of this project. Users are solely responsible for how they use, modify, or distribute this code. By using this project, you agree that you are responsible for ensuring your actions are legal, ethical, and authorized.

If you are unsure whether your use is allowed, do not use this project.

## Documentation

- [Documentation website](https://campus-auth-java.netlify.app)
- [Quickstart](https://campus-auth-java.netlify.app/quickstart/)
- [API guide](https://campus-auth-java.netlify.app/api/)
- [Security notes](SECURITY.md)
- [Roadmap](ROADMAP.md)
- [Changelog](CHANGELOG.md)
- [Release notes](RELEASE_NOTES.md)

## Contributing

Contributions are welcome when they improve reliability, safety, documentation, or maintainability. Read [CONTRIBUTING.md](CONTRIBUTING.md) before opening a pull request and follow the [Code of Conduct](CODE_OF_CONDUCT.md).

## License

Distributed under the MIT License. See [LICENSE](LICENSE) for details.

## Author

**Mahmudkhan Usmanov**

- GitHub: [@UsmanovMahmudkhan](https://github.com/UsmanovMahmudkhan)
- LinkedIn: [Mahmudkhan Usmanov](https://www.linkedin.com/in/mahmudkhon-u-57b202249/)
