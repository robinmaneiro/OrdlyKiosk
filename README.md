# OrderKiosk

An Android self-service kiosk application for food and restaurant ordering. Customers can browse menus, manage a bag, apply coupons, and complete orders — with support for both guest and authenticated sessions.

## Features

- **Welcome & onboarding** — dining option selection (eat-in / take-away)
- **Menu browsing** — categories, products, and extended item details
- **Offers & coupons** — promotional content and discount codes
- **Bag management** — add, remove, and update items; bag merging on login
- **Checkout & order summary** — order completion with QR code display
- **Order history** — view previous orders
- **Account management** — registration, login, password reset, and personal details (email, phone, DOB)
- **Connectivity awareness** — offline detection with user-facing dialogs

## Tech Stack

| Area | Technology |
|---|---|
| Language | Kotlin 2.3.0 |
| UI | Jetpack Compose + Material 3 |
| Architecture | Clean Architecture + MVVM |
| DI | Koin 4.1.1 |
| Networking | Ktor 3.3.1 |
| Serialization | Jackson |
| Persistence | DataStore Preferences + Tink (encrypted) |
| Images | Coil 3.3.0 |
| Animations | Lottie 6.7.1 |
| QR codes | ZXing 4.3.0 |
| Logging | Timber |
| Async | Kotlin Coroutines + StateFlow |

## Architecture

The project follows **Clean Architecture** with a clear separation between layers:

```
Presentation  →  ViewModels, Compose screens, UI events/actions
Domain        →  Use cases (single-responsibility, return Result<T>)
Data          →  Repositories, Ktor NetworkManager, DataStore
```

State is managed via `StateFlow`, with `Channel`-based events for one-shot side effects like navigation. Dependency injection is handled by Koin, with modules split by feature.

## Project Structure

```
app/src/main/kotlin/com/robinmaneiro/
├── account/         # Login, registration, password reset, my account
├── auth/            # Guest session + token refresh logic
├── bag/             # Cart management
├── checkout/        # Order completion
├── coupons/         # Coupon management
├── menu/            # Product catalogue
├── offers/          # Promotions
├── orderhistory/    # Past orders
├── ordersummary/    # Post-purchase confirmation
├── welcome/         # Entry screen
├── networking/      # Ktor client + connectivity observer
├── datastore/       # Encrypted preference storage
├── ui/              # Shared composables, theme, buttons
├── util/            # Extensions and helpers
└── koin/            # DI module configuration
```

## Requirements

- **Min SDK:** 25 (Android 7.1)
- **Target SDK:** 36 (Android 15)
- **JDK:** 17

## Getting Started

1. Clone the repository
2. Open in Android Studio
3. Sync Gradle dependencies
4. Run on a device or emulator (API 25+)

## Code Quality

The project uses [Detekt](https://detekt.dev/) for static analysis, with rules covering:

- Compose best practices (via `detekt-compose`)
- Formatting (via `detekt-formatting` / Ktlint)
- Complexity thresholds (cyclomatic complexity ≤ 15, method length ≤ 60 lines)

Run the check with:

```bash
./gradlew detekt
```

## Testing

Unit tests cover all use cases using JUnit 4 and MockK.

```bash
./gradlew test
```
