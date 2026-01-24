This is a Kotlin Multiplatform project targeting Android, iOS.

* [/composeApp](./composeApp/src) is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - [commonMain](./composeApp/src/commonMain/kotlin) is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    the [iosMain](./composeApp/src/iosMain/kotlin) folder would be the right place for such calls.
    Similarly, if you want to edit the Desktop (JVM) specific part, the [jvmMain](./composeApp/src/jvmMain/kotlin)
    folder is the appropriate location.

* [/iosApp](./iosApp/iosApp) contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform,
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.

### Build and Run Android Application

To build and run the development version of the Android app, use the run configuration from the run widget
in your IDE’s toolbar or build it directly from the terminal:
- on macOS/Linux
  ```shell
  ./gradlew :composeApp:assembleDebug
  ```
- on Windows
  ```shell
  .\gradlew.bat :composeApp:assembleDebug
  ```

### Build and Run iOS Application

To build and run the development version of the iOS app, use the run configuration from the run widget
in your IDE’s toolbar or open the [/iosApp](./iosApp) directory in Xcode and run it from there.

---

## Architecture & Implementation Notes

### Publications Feature
- **PublicationApi** - HTTP client for fetching publications from `/publications` endpoint with pagination and filtering
- **PublicationRepository** - Maps DTOs to domain models (Publication, PublicationFilter, PublicationListResponse)
- **HomeScreen** - Displays publications in a LazyColumn with author, content, views, and likes
- Uses MVI pattern (HomeIntent/HomeState/HomeViewModel)

### Authentication & Cookie Management
- **PreferencesRepository** implements `CookiesStorage` - single source of truth for tokens and cookies
  - Stores `access_token` and `refresh_token` in DataStore (persistent across app restarts)
  - Ktor HttpClient automatically reads/writes cookies via PreferencesRepository
- **Backend sends tokens via Set-Cookie headers** (httpOnly, Secure)
- **Ktor automatically:**
  - Saves received cookies to PreferencesRepository
  - Sends cookies with each request (via CookiesStorage interface)
  - Works transparently without manual Authorization header management

### Key Design Decisions
- Single source of truth: PreferencesRepository for all authentication state
- CookiesStorage abstraction integrated into PreferencesRepository (no separate adapter class)
- API classes don't know about authentication - HttpClient handles it automatically
- Domain models separate from DTOs with mapper layer (DTO ↔ Domain conversion)

---

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…