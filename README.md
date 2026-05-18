<div align="center">
  <h1>Campus Auth Java</h1>
  <p><b>A lightweight, robust, and extensible Java library for campus member account authentication.</b></p>

  <!-- Badges -->
  <p>
    <img src="https://img.shields.io/badge/Java-11%2B-blue?logo=java&logoColor=white" alt="Java 11+" />
    <img src="https://img.shields.io/badge/Maven-3.8%2B-red?logo=apachemaven&logoColor=white" alt="Maven" />
    <img src="https://img.shields.io/badge/License-MIT-green.svg" alt="License: MIT" />
  </p>
</div>

---

## About The Project

**Campus Auth Java** is a high-performance library and CLI tool designed to perform login checks against various supported campus services. It elegantly abstracts away the complex session management, HTML parsing, and HTTP requests required to authenticate and extract metadata for campus members. 

Whether you need to verify student credentials for a club app or extract major/grade information for a custom portal, this library provides a clean, unified API returning structured JSON responses.

## Key Features

- **Multiple Authentication Strategies:** Support for Portal SSO, Classic Sessions, Moodler, and DoSejong.
- **Structured JSON Results:** Consistent response formats including success state, status codes, and user metadata (name, major, grade, etc.).
- **Smart Fallback:** A "Manual" mode that intelligently cascades through authenticators until a definite result is found.
- **Extensible Architecture:** Easily add new authenticators for different campus endpoints.
- **CLI & Library Support:** Use it programmatically in your Java applications or directly from the terminal.
- **Lightweight:** Minimal dependencies, leveraging native Java `HttpClient` and JSoup for fast HTML parsing.

## Tech Stack

- **Language:** Java 11
- **Build Tool:** Maven 3.8+
- **HTTP Client:** Native `java.net.http.HttpClient`
- **HTML Parser:** JSoup (1.17.2)

## Folder Structure

```text
campus-auth-java/
├── src/
│   └── main/
│       └── java/
│           └── campus/auth/java/
│               ├── Main.java                # CLI Entry Point
│               ├── AuthService.java         # Core Authentication Service
│               ├── AuthMethod.java          # Authenticator Enums
│               ├── AuthResult.java          # Result Data Models
│               ├── authenticators/          # Authentication Implementations
│               └── exceptions/              # Custom Exceptions
├── pom.xml                                  # Maven Configuration
├── LICENSE                                  # MIT License
└── README.md                                # Project Documentation
```

## Getting Started

### Prerequisites

- **Java Development Kit (JDK) 11** or higher
- **Apache Maven 3.8** or higher

### Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/UsmanovMahmudkhan/campus-auth-java.git
   cd campus-auth-java
   ```

2. **Build the project:**
   Compile the source code and package it into a fat jar.
   ```bash
   mvn clean package
   ```
   This will generate a shaded jar file at `target/campus-auth-java-0.3.4.jar`.

## Usage Guide

### 1. Command Line Interface (CLI)

The CLI outputs a structured JSON string containing the authentication result.

**Basic Usage (Automatic Fallback):**
Uses the default sequence (`PortalSSOToken` -> `ClassicSession` -> `MoodlerSession` -> `DosejongSession`).
```bash
java -jar target/campus-auth-java-0.3.4.jar <student_id> <password>
```

**Interactive Password Prompt:**
Omit the password to be prompted securely.
```bash
java -jar target/campus-auth-java-0.3.4.jar <student_id>
```

**Specific Authenticators:**
```bash
java -jar target/campus-auth-java-0.3.4.jar <student_id> <password> PortalSSOToken
java -jar target/campus-auth-java-0.3.4.jar <student_id> <password> PortalSSOToken,DosejongSession
```

### 2. Using as a Library

Include the classes in your project and call `AuthService`.

```java
import campus.auth.java.AuthResult;
import campus.auth.java.AuthService;
import campus.auth.java.AuthMethod;
import campus.auth.java.AuthResponse;

public class App {
    public static void main(String[] args) {
        // Manual fallback mode
        AuthResult result = AuthService.authenticate("student_id", "password");
        System.out.println(result.toJson());

        // Specific authenticator
        AuthResponse portalResult = AuthService.authenticate(
            "student_id", 
            "password", 
            AuthMethod.PORTAL_SSO_TOKEN
        );
        System.out.println(portalResult.getIsAuth());
    }
}
```

#### Implemented Authenticators

| Method | Description | Extracted Metadata |
| :--- | :--- | :--- |
| `PortalSSOToken` | Checks campus portal SSO token behavior via Blackboard. | Authentication Status |
| `ClassicSession` | Checks Daeyang Humanity College session. | Classic Reading Certification |
| `MoodlerSession` | Checks SJULMS Moodler session login. | Name, Major |
| `DosejongSession`| Checks Do Sejong session login. | Name, Major |
| `Manual`         | Cascades through all methods. | Varies by successful method |

_Legacy Helpers available in `LegacyAuth` for backward compatibility (`dosejongApi`, `uisApi`, `sjlmsApi`)._

## Environment Variables

This project currently does not strictly require any `.env` configurations. All credentials are passed directly via CLI arguments or method parameters. 
> **Security Warning:** Never hardcode or commit real credentials to your version control system.

## Documentation

- [README.md](README.md): project overview, build steps, and usage examples.
- [CHANGELOG.md](CHANGELOG.md): version history.
- [RELEASE_NOTES.md](RELEASE_NOTES.md): release summary for the current version.
- [LICENSE](LICENSE): MIT license terms.

## Roadmap

- [ ] Add asynchronous authentication support via `CompletableFuture`.
- [ ] Implement robust retry mechanisms for intermittent network timeouts.
- [ ] Create a comprehensive suite of Unit and Integration Tests.
- [ ] Publish library to Maven Central.

## Known Limitations

- **Timeout Restrictions:** HTTP requests are hard-coded to a 3-second timeout.
- **DOM Dependency:** `JSoup` HTML parsing relies on the current structure of the university web pages. Upstream UI changes may break metadata extraction.

## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

Distributed under the MIT License. See `LICENSE` for more information.

## Author

**Mahmudkhan Usmanov**
- GitHub: [@UsmanovMahmudkhan](https://github.com/UsmanovMahmudkhan)
- LinkedIn: [Mahmudkhan Usmanov](https://www.linkedin.com/in/mahmudkhonusmonov/)
