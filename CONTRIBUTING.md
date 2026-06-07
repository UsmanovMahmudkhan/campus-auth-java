# Contributing

Thank you for helping improve campus-auth-java. Contributions should make the
project safer, clearer, easier to maintain, or more reliable.

## Ground rules

- Follow the [Code of Conduct](CODE_OF_CONDUCT.md).
- Do not submit code that enables abuse, unauthorized access, privacy
  violations, or credential misuse.
- Do not add integrations that log into, scrape, or automate real third-party
  authentication systems. See [SECURITY.md](SECURITY.md).
- Never include real credentials, tokens, cookies, real ids, private endpoints,
  screenshots, or logs containing personal data. Use the demo values only.
- Keep changes focused; separate unrelated refactors and behavior changes.

## Local setup

```bash
git clone https://github.com/UsmanovMahmudkhan/campus-auth-java.git
cd campus-auth-java
mvn -B clean verify
```

Requires JDK 17 or newer and Maven 3.8 or newer.

## Pull request checklist

- Run `mvn -B clean verify` and confirm it passes.
- Add or update tests for the behavior you touch.
- Update `README.md` and `CHANGELOG.md` when behavior changes.
- Confirm no secrets, cookies, real ids, or personal data are included.
- Explain what changed, why, and how you verified it.

## Releasing to Maven Central

Releases are published to Maven Central through the Sonatype Central Publisher
Portal. The `release` Maven profile enables GPG signing and the
`central-publishing-maven-plugin`.

One-time setup:

1. Create an account at [central.sonatype.com](https://central.sonatype.com/).
2. Verify the namespace `io.github.usmanovmahmudkhan` (GitHub namespaces are
   verified by proving ownership of the matching GitHub account).
3. Generate a Central Portal user token (username + password pair).
4. Generate a GPG key, publish its public part to a keyserver, and export the
   private key.
5. Add the repository secrets listed below.

Required GitHub Actions secrets:

| Secret             | Purpose                                  |
| ------------------ | ---------------------------------------- |
| `CENTRAL_USERNAME` | Central Portal token username            |
| `CENTRAL_PASSWORD` | Central Portal token password            |
| `GPG_PRIVATE_KEY`  | ASCII-armored exported private key        |
| `GPG_PASSPHRASE`   | Passphrase for that key                  |

To publish, run the **Release** workflow from the Actions tab
(`workflow_dispatch`). It builds, signs, and uploads `0.1.0` with
`mvn -B -Prelease deploy`. A local equivalent:

```bash
mvn -B -Prelease clean deploy
```

## Security reports

Do not open public issues for sensitive vulnerabilities. Use the process in
[SECURITY.md](SECURITY.md).
