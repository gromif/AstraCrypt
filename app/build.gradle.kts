import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.astracrypt.android.application)
    alias(libs.plugins.astracrypt.android.application.compose)
    alias(libs.plugins.astracrypt.kotlin.coroutines)
    alias(libs.plugins.astracrypt.kotlin.serialization)
    alias(libs.plugins.astracrypt.android.hilt)
    alias(libs.plugins.astracrypt.android.hilt.compose)
    alias(libs.plugins.astracrypt.android.room)
    alias(libs.plugins.astracrypt.android.paging)
    alias(libs.plugins.kotlin.navigation.safeargs)
}

// Create a variable called keystorePropertiesFile, and initialize it to your
// keystore.properties file, in the rootProject folder.
val keystorePropertiesFile = rootProject.file("keystore.properties")

// Initialize a new Properties() object called keystoreProperties.
val keystoreProperties = Properties()

// Load your keystore.properties file into the keystoreProperties object.
keystoreProperties.load(FileInputStream(keystorePropertiesFile))

android {
    namespace = "com.nevidimka655.astracrypt"

    defaultConfig {
        buildFeatures.run {
            viewBinding = true
            buildConfig = true
        }
        signingConfigs {
            create("release") {
                keyAlias = keystoreProperties["keyAlias"] as String
                keyPassword = keystoreProperties["keyPassword"] as String
                storeFile = file(keystoreProperties["storeFile"] as String)
                storePassword = keystoreProperties["storePassword"] as String
            }
        }
        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            isDebuggable = true
            isJniDebuggable = true
            signingConfig = signingConfigs.getByName("release")
        }
        release {
            isShrinkResources = true
            isMinifyEnabled = true
            isJniDebuggable = false
            isDebuggable = false
            setProguardFiles(
                listOf(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    file("proguard-rules.pro")
                )
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }
}

dependencies {
    implementation(projects.core.resources)
    implementation(project(":core:haptic"))
    implementation(project(":core:tiles-with-coroutines"))

    implementation(projects.core.tink)
    implementation(projects.features.tinkLab)

    implementation(projects.data.notes)
    implementation(projects.features.composeNotes)

    implementation(projects.di.core)

    implementation(project(":ui:compose-calculator"))
    implementation(project(":ui:compose-color-schemes"))
    implementation(project(":ui:compose-details"))
    implementation(project(":ui:compose-help"))

    // Navigation
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)

    // Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.activity)
    implementation(libs.compose.navigation)
    implementation(libs.compose.material3.icons.core)
    implementation(libs.compose.material3.icons.extended)
    implementation(libs.compose.material3)
    implementation(libs.compose.material3.windowSizeClass)
    debugImplementation(libs.compose.ui)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.tooling.preview)
    debugImplementation(libs.compose.ui.test.manifest)

    // Background
    implementation(libs.androidx.work.runtime.ktx)

    // Lifecycle
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.lifecycle.runtime.compose)

    // Media
    implementation(libs.media.coil.compose)
    implementation(libs.media.coil.video)
    implementation(libs.media.exifinterface)

    // Google
    implementation(libs.google.crypto.tink)

    // DI - Hilt
    implementation(libs.hilt.work)

    // Other
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.datastore.preferences)
}