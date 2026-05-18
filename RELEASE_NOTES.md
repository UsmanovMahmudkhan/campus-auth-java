# Release Notes

## campus-auth-java 0.3.4

This release introduces the first complete Java package for `campus-auth-java`.

### Highlights

- Maven-based Java 11 project structure.
- Runnable CLI with JSON output.
- Library API for calling authentication flows directly from Java applications.
- Multiple authenticator strategies with manual fallback support.
- Structured result objects for success state, authentication state, status code, result code, metadata, and authenticator name.
- MIT license, GitHub-ready README, changelog, release notes, and Java-focused `.gitignore`.

### Build

```bash
mvn clean package
```

### Run

```bash
java -jar target/campus-auth-java-0.3.4.jar <student_id> <password>
```
