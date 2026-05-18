# Roadmap

Campus Auth Java is currently focused on a small, maintainable authentication toolkit for authorized campus-account verification workflows.

## Near Term

- Add unit tests for result models, JSON serialization, method parsing, and fallback behavior.
- Add integration-test harnesses that use safe mock services instead of real credentials.
- Improve retry handling for intermittent network failures.
- Add asynchronous APIs using `CompletableFuture`.
- Expand result codes for clearer service failure diagnosis.

## Documentation

- Keep the Netlify documentation site aligned with the README.
- Add examples for each authenticator.
- Document safe handling of credentials and test data.

## Packaging

- Prepare Maven Central publishing metadata.
- Add release checklist automation.
- Add signed release artifacts when the package is ready for wider distribution.

## Safety

- Keep explicit authorization and misuse warnings visible.
- Avoid storing or logging credentials, cookies, tokens, or private member data.
- Prefer mockable abstractions for future tests and demos.
