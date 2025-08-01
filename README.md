# AR Placement App

A simple Android application demonstrating augmented reality (AR) functionality using Google's Sceneform and ARCore. The app allows users to select drill options and place 3D models in an AR environment.

## Features
- **Main Activity**: Launcher screen with a button to navigate to the drill selection screen.
- **Drill Selection Activity**: Interface for selecting drill options before entering AR mode.
- **AR Activity** (if applicable): Renders AR content using Sceneform’s `ArFragment` for placing 3D models in the real world.
- ARCore integration for camera-based AR experiences.

## Prerequisites
- **Android Studio**: Version 2023.3.1 or later (e.g., Iguana or newer).
- **Android Device**: ARCore-compatible device with Android 7.0 (API 24) or higher. Check supported devices: [Google ARCore Supported Devices](https://developers.google.com/ar/devices).
- **Google Play Services for AR**: Installed on the device (automatically prompted if missing).
- **Java 11**: Required for compilation.

## Setup Instructions

### 1. Clone the Repository
```bash
git clone <repository-url>
cd arplacementapp
```

### 2. Project Structure
- **app/src/main/java/com/example/arplacementapp/**: Kotlin source files (`MainActivity.kt`, `DrillSelectionActivity.kt`, `ArActivity.kt`).
- **app/src/main/res/layout/**: Layout files (`activity_main.xml`, `activity_drill_selection.xml`, `activity_ar.xml`).
- **gradle/libs.versions.toml**: Version catalog for dependency management.
- **AndroidManifest.xml**: Declares activities, permissions, and ARCore requirements.
- **build.gradle** (top-level): Configures Gradle plugins.
- **app/build.gradle**: Defines app dependencies and build settings.
- **settings.gradle**: Configures repositories (Google, Maven Central).

### 3. Gradle Configuration
The project uses a version catalog (`libs.versions.toml`) for dependency management. Key settings:
- **Plugins**: Android Gradle Plugin 8.5.2, Kotlin 2.0.20.
- **Dependencies**:
  - `com.google.ar.sceneform.ux:sceneform-ux:1.15.0` (Sceneform for AR rendering).
  - `com.google.ar:core:1.44.0` (ARCore SDK).
  - AndroidX libraries for UI and compatibility.
- **Exclusions**: Excludes `com.android.support:support-compat` and `support-v4` to avoid conflicts with AndroidX.
- **Compile SDK**: Set to 36 (Android 16), with a warning suppression for AGP 8.5.2 compatibility.

**Key Files**:
- **`gradle/libs.versions.toml`**:
  ```toml
  [versions]
  androidGradlePlugin = "8.5.2"
  kotlin = "2.0.20"
  androidxCoreKtx = "1.13.1"
  androidxAppcompat = "1.7.0"
  material = "1.12.0"
  androidxActivity = "1.9.2"
  androidxConstraintlayout = "2.1.4"
  sceneform = "1.15.0"
  arcore = "1.44.0"
  junit = "4.13.2"
  androidxJunit = "1.2.1"
  androidxEspressoCore = "3.6.1"

  [libraries]
  androidx-core-ktx = { module = "androidx.core:core-ktx", version.ref = "androidxCoreKtx" }
  androidx-appcompat = { module = "androidx.appcompat:appcompat", version.ref = "androidxAppcompat" }
  material = { module = "com.google.android.material:material", version.ref = "material" }
  androidx-activity = { module = "androidx.activity:activity-ktx", version.ref = "androidxActivity" }
  androidx-constraintlayout = { module = "androidx.constraintlayout:constraintlayout", version.ref = "androidxConstraintlayout" }
  sceneform-ux = { module = "com.google.ar.sceneform.ux:sceneform-ux", version.ref = "sceneform" }
  arcore = { module = "com.google.ar:core", version.ref = "arcore" }
  junit = { module = "junit:junit", version.ref = "junit" }
  androidx-junit = { module = "androidx.test.ext:junit", version.ref = "androidxJunit" }
  androidx-espresso-core = { module = "androidx.test.espresso:espresso-core", version.ref = "androidxEspressoCore" }

  [plugins]
  android-application = { id = "com.android.application", version.ref = "androidGradlePlugin" }
  kotlin-android = { id = "org.jetbrains.kotlin.android", version.ref = "kotlin" }
  ```
- **`app/build.gradle`**:
  ```gradle
  plugins {
      alias(libs.plugins.android.application)
      alias(libs.plugins.kotlin.android)
  }

  android {
      namespace = "com.example.arplacementapp"
      compileSdk = 36
      defaultConfig {
          applicationId = "com.example.arplacementapp"
          minSdk = 24
          targetSdk = 36
          versionCode = 1
          versionName = "1.0"
          testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
      }
      buildTypes {
          release {
              isMinifyEnabled = false
              proguardFiles(
                  getDefaultProguardFile("proguard-android-optimize.txt"),
                  "proguard-rules.pro"
              )
          }
      }
      compileOptions {
          sourceCompatibility = JavaVersion.VERSION_11
          targetCompatibility = JavaVersion.VERSION_11
      }
      kotlinOptions {
          jvmTarget = "11"
      }
      buildFeatures {
          viewBinding = true
      }
  }

  configurations.all {
      exclude(group = "com.android.support", module = "support-compat")
      exclude(group = "com.android.support", module = "support-v4")
  }

  dependencies {
      implementation(libs.androidx.core.ktx)
      implementation(libs.androidx.appcompat)
      implementation(libs.material)
      implementation(libs.androidx.activity)
      implementation(libs.androidx.constraintlayout)
      implementation(libs.sceneform.ux)
      implementation(libs.arcore)
      testImplementation(libs.junit)
      androidTestImplementation(libs.androidx.junit)
      androidTestImplementation(libs.androidx.espresso.core)
  }
  ```
- **`settings.gradle`**:
  ```gradle
  pluginManagement {
      repositories {
          google()
          mavenCentral()
          gradlePluginPortal()
      }
  }
  dependencyResolutionManagement {
      repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
      repositories {
          google()
          mavenCentral()
      }
  }
  rootProject.name = "arplacementapp"
  include(":app")
  ```

### 4. AndroidManifest.xml
The manifest declares activities and ARCore requirements:
```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">
    <uses-permission android:name="android.permission.CAMERA" />
    <uses-feature android:name="android.hardware.camera.ar" android:required="true" />
    <uses-feature android:name="android.hardware.camera" android:required="true" />
    <application
        android:allowBackup="true"
        android:dataExtractionRules="@xml/data_extraction_rules"
        android:fullBackupContent="@xml/backup_rules"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.Arplacementapp">
        <activity
            android:name=".MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <activity
            android:name=".DrillSelectionActivity"
            android:exported="false" />
        <activity
            android:name=".ArActivity"
            android:exported="false"
            android:screenOrientation="portrait" />
        <meta-data android:name="com.google.ar.core" android:value="required" />
    </application>
</manifest>
```

### 5. Build the Project
1. Open the project in Android Studio.
2. Sync the project with Gradle:
   - Click `File > Sync Project with Gradle Files`.
   - Or run:
     ```bash
     ./gradlew clean build
     ```
3. If build issues occur, clear Gradle cache:
   ```bash
   ./gradlew cleanBuildCache
   rm -rf ~/.gradle/caches/
   ```

### 6. Suppress compileSdk Warning
The project uses `compileSdk = 36`, which triggers a warning with AGP 8.5.2 (tested up to API 34). Add to `gradle.properties` (create if missing in project root):
```properties
android.suppressUnsupportedCompileSdk=36
```

**Alternative**: Downgrade to `compileSdk = 34` in `app/build.gradle` if Android 16 features are not required:
```gradle
android {
    compileSdk = 34
    defaultConfig {
        targetSdk = 34
        // ...
    }
    // ...
}
```

### 7. Run the App
1. Connect an ARCore-compatible Android device or use an emulator with ARCore support.
2. Ensure Google Play Services for AR is installed (prompted automatically if missing).
3. Run the app:
   - In Android Studio: Click `Run > Run 'app'`.
   - Or use:
     ```bash
     ./gradlew installDebug
     ```
4. Navigate through the app:
   - **MainActivity**: Click the button to open `DrillSelectionActivity`.
   - **DrillSelectionActivity**: Select a drill option to proceed to `ArActivity` (if implemented).
   - **ArActivity**: Interact with AR content using Sceneform’s `ArFragment`.

### 8. AR Setup
- Ensure layouts (e.g., `activity_ar.xml`) include:
  ```xml
  <fragment
      android:id="@+id/arFragment"
      android:name="com.google.ar.sceneform.ux.ArFragment"
      android:layout_width="match_parent"
      android:layout_height="match_parent" />
  ```
- Verify the device supports ARCore: [ARCore Supported Devices](https://developers.google.com/ar/devices).

### 9. Troubleshooting
- **ActivityNotFoundException**:
  - Ensure all activities (`MainActivity`, `DrillSelectionActivity`, `ArActivity`) are declared in `AndroidManifest.xml`.
  - Verify intents in `MainActivity.kt` or `DrillSelectionActivity.kt` (e.g., `startActivity(Intent(this, DrillSelectionActivity::class.java))`).
- **Dependency Conflicts**:
  - The `configurations.all` block excludes `com.android.support` to prevent AndroidX conflicts. If duplicate class errors occur, run:
    ```bash
    ./gradlew app:dependencies
    ```
  - Check Sceneform documentation: [Google Sceneform](https://developers.google.com/sceneform).
- **Build Errors**:
  - Run with detailed output:
    ```bash
    ./gradlew build --stacktrace --debug
    ```
- **ARCore Not Working**:
  - Install Google Play Services for AR from the Play Store.
  - Ensure the device is ARCore-compatible.

### 10. Dependencies
- **Gradle Plugins**:
  - `com.android.application:8.5.2`
  - `org.jetbrains.kotlin.android:2.0.20`
- **Libraries**:
  - `androidx.core:core-ktx:1.13.1`
  - `androidx.appcompat:appcompat:1.7.0`
  - `com.google.android.material:material:1.12.0`
  - `androidx.activity:activity-ktx:1.9.2`
  - `androidx.constraintlayout:constraintlayout:2.1.4`
  - `com.google.ar.sceneform.ux:sceneform-ux:1.15.0`
  - `com.google.ar:core:1.44.0`
  - Test libraries: `junit:junit:4.13.2`, `androidx.test.ext:junit:1.2.1`, `androidx.test.espresso:espresso-core:3.6.1`

### 11. Notes
- The app uses Google’s Sceneform (`1.15.0`) to avoid JitPack issues with the community fork (`com.github.thomasgorisse:SceneformAndroidSDK`).
- To use the community Sceneform fork, clone it locally and include as a module:
  ```bash
  git clone https://github.com/thomasgorisse/SceneformAndroidSDK.git
  ```
  Update `settings.gradle`:
  ```gradle
  include ':sceneform'
  project(':sceneform').projectDir = new File('SceneformAndroidSDK')
  ```
  Update `app/build.gradle`:
  ```gradle
  dependencies {
      implementation project(':sceneform')
  }
  ```
- For Android 16 (API 36) support, monitor AGP updates for full compatibility: [Android API Level Support](https://d.android.com/r/tools/api-level-support).

### 12. Contributing
- Submit bug reports or feature requests via GitHub issues.
- For code contributions, create a pull request with clear descriptions.

### 13. License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

### Customization Notes
- **Repository URL**: Replace `<repository-url>` with your actual repository URL.
- **Features**: Expand the “Features” section if your app has specific AR models, gestures, or drill selection logic.
- **ArActivity**: The README includes `ArActivity` based on prior context. Remove it if your app only uses `MainActivity` and `DrillSelectionActivity`.
- **Screenshots**: Add screenshots or a demo GIF to showcase the app’s UI and AR functionality.
- **Local Module**: The “Notes” section includes optional instructions for using `SceneformAndroidSDK` locally, in case you need its features later.

### Verification
- The README reflects your current Gradle setup (`compileSdk = 36`, Sceneform `1.15.0`, global `com.android.support` exclusions).
- The `compileSdk = 36` warning is addressed with a `gradle.properties` solution or an alternative downgrade to 34.
- The manifest includes all activities (`MainActivity`, `DrillSelectionActivity`, `ArActivity`) to prevent `ActivityNotFoundException`.
