# Security Policy

## Supported versions

| Version | Supported |
| :------ | :-------- |
| `0.1.0` | Yes       |

## Intended use

This project is an educational/demo JVM package. It ships only an in-memory
demo provider and performs no real authentication. It is not affiliated with
Sejong University or any institution.

**Do not use this project, or any provider you build on top of it, to automate,
bypass, brute force, scrape, or otherwise attack a real authentication system
you are not explicitly authorized to test.** Unauthorized access, privacy
violations, abuse, and disruption are out of scope and unsupported.

## Permission-only integration policy

The package intentionally contains no integration with any real campus,
university, or third-party login system. If you implement a custom
`AuthProvider` against a real directory:

- You must own the system or have explicit written authorization to integrate
  with it.
- Keep that integration in your own application, not in this repository or any
  public example.
- Never commit real endpoints, headers, cookies, tokens, or credentials.

## Do not submit real credentials

Never put real credentials, real student or staff ids, passwords, tokens,
cookies, session data, or private endpoint details into issues, pull requests,
logs, screenshots, tests, or code. Use the demo values
(`demo-student` / `demo-password`) for all examples and reports. If you
accidentally expose a secret, rotate it immediately.

## Responsible disclosure

Please do not open public issues for vulnerabilities, credential leaks, or
bypass techniques.

Report security concerns privately through GitHub's
[private vulnerability reporting](https://github.com/UsmanovMahmudkhan/campus-auth-java/security/advisories/new)
for this repository. Include:

- A short description of the issue.
- Steps to reproduce using only non-sensitive demo data.
- The affected version or commit.
- Any suggested fix or mitigation.

We aim to acknowledge reports within a few business days.
