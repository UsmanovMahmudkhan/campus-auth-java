# Contributing

Thank you for helping improve Campus Auth Java. Contributions should make the project safer, clearer, easier to maintain, or more reliable for authorized use.

## Ground Rules

- Follow the [Code of Conduct](CODE_OF_CONDUCT.md).
- Do not submit code that enables abuse, unauthorized access, privacy violations, or credential misuse.
- Do not include real credentials, tokens, cookies, student data, private screenshots, or logs containing personal information.
- Keep changes focused. Separate unrelated refactors, docs edits, and behavior changes into different pull requests when practical.

## Local Setup

```bash
git clone https://github.com/UsmanovMahmudkhan/campus-auth-java.git
cd campus-auth-java
mvn clean package
```

The project requires JDK 11 or newer and Maven 3.8 or newer.

## Branches and Commits

- Use descriptive branch names such as `feature/add-authenticator`, `fix/json-output`, or `docs/security-notes`.
- Write short commit messages that describe the user-visible change.
- Keep generated build output out of commits. The `target/` directory is ignored.

## Pull Request Checklist

Before opening a pull request:

- Run `mvn clean package`.
- Update `README.md`, `CHANGELOG.md`, or docs pages when behavior changes.
- Add or update tests when the project has test coverage for the touched behavior.
- Confirm no secrets, cookies, private identifiers, or personal data are included.
- Explain what changed, why it changed, and how it was verified.

## Adding an Authenticator

New authenticators should:

- Implement the shared `Authenticator` contract.
- Return structured `AuthResponse` data with clear result codes.
- Use explicit timeouts and avoid indefinite network waits.
- Treat unknown service responses as unknown, not as credential failures.
- Avoid logging credentials, tokens, cookies, or private metadata.

## Security Reports

Do not open public issues for sensitive vulnerabilities. Use the process in [SECURITY.md](SECURITY.md).
