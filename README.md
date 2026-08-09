# Order Kiosk

Self-service restaurant kiosk app built with Jetpack Compose. Browse menu, manage bag, place orders.

## Architecture

Clean Architecture with multi-module setup:

```
:app                  UI, features, navigation
:core:network         Ktor HTTP client, token refresh, connectivity observer
:core:datastore       DataStore + Tink encryption for session/token storage
```

The core modules expose interfaces (`TokenProvider`, `TokenRefresher`, `DataStoreRepository`) and the app provides implementations via Koin. This keeps the dependency graph clean and avoids circular references between networking and auth.

ViewModels use `StateFlow` for UI state and `Channel` for one-shot navigation events.

## Tech stack

- Jetpack Compose + Material 3
- Ktor Client (OkHttp engine) with Jackson
- Koin for DI
- DataStore Preferences with Tink AES-256-GCM encryption
- Detekt for static analysis
- GitHub Actions CI

## Testing

Tests use fakes instead of mocks. Each repository has a configurable fake with `Result` properties you set per test. `TestData` provides factory methods with sensible defaults so tests stay readable.

Turbine is used for testing `Channel` emissions (e.g. navigation events).

```bash
./gradlew testDebugUnitTest
```

## Building

Requires JDK 17.

```bash
./gradlew assembleDebug
./gradlew detekt                                    # lint
./gradlew detekt assembleDebug testDebugUnitTest     # full CI check
```

Build variants: `debug` (local server), `debugQA` / `qa` (QA API), `release` (production).
