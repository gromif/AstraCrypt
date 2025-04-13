<picture>
    <source media="(prefers-color-scheme: dark)" srcset="docs/assets/feature_graphic.png">
    <img alt="Feature Graphic" src="docs/assets/feature_graphic.png">
</picture>

# AstraCrypt - Encrypt Your Data

AstraCrypt - is a free, powerful and open-source encryption app that has everything you need to keep your data safe and secure.
## Features
-    **Strong Encryption:** Uses X/AES256-GCM and other Authenticated Encryption with Additional Data (AEAD) algorithms.
-    **Multi-Encryption:** Supports using multiple encryption algorithms on user data.
-    **Secure Storage:** Securely stores data without sacrificing usability.
-    **Authentication Layer:** Optional user authentication/mask for added security.
-    **External Storage Support:** Encrypt/decrypt data to/from external devices.
-    **Custom Security Settings:** Advanced configuration options for tailored protection.
-    **Device Admin Tools:** Includes device administration capabilities.
-    **Modern UI:** Built with intuitive, Material You design.
-    **Navigation Support:** Offers basic data navigation within the app.
-    And more!
## Screenshots
<picture>
    <source media="(prefers-color-scheme: dark)" srcset="docs/assets/feature_graphic.png">
    <img alt="Screenshots" src="docs/assets/screenshots.png">
</picture>

## Download
<p align="left">
	<a href="https://github.com/gromif/AstraCrypt/releases/latest">
    <picture>
      <source media="(prefers-color-scheme: dark)" srcset="docs/assets/badge-github.png" height="60">
      <img alt="Get it on GitHub" src="docs/assets/badge-github.png" height="60">
    </picture>
  </a>
  <a href="https://play.google.com/store/apps/details?id=com.nevidimka655.astracrypt">
    <picture>
      <source media="(prefers-color-scheme: dark)" srcset="docs/assets/badge-google-play.png" height="60">
      <img alt="Get it on Google Play" src="docs/assets/badge-google-play.png" height="60">
    </picture>
  </a>
</p>
AstraCrypt on Play Store will install as a different app from other sources. Some features may be restricted to comply with Google Play's publishing rules.

## Tech Stack
-    **Architecture:** Clean Architecture (multi-modular). Presentation Pattern - MVVM.
-    **Build:** Custom (convention plugins).
-    **Security:** Google Tink.
-    **Database:** AndroidX Room & Paging.
-    **Background:** Kotlin Coroutines, AndroidX Work.
-    **Multimedia:** Coil, Exif-Interface, AndroidX DocumentFile.
-    **UI Framework:** Jetpack Compose & Metrics.
-    **Navigation:** AndroidX Navigation-Compose.
-    **Unit Tests:** Junit v4, Mockk, Robolectric, KotlinX Coroutines Test.
-    **Persistent Data:** AndroidX DataStore, KotlinX Serialization, Kotlin Parcelize.