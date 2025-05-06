import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.astracrypt.android.application)
    alias(libs.plugins.astracrypt.android.application.compose)
    alias(libs.plugins.astracrypt.kotlin.coroutines)
    alias(libs.plugins.astracrypt.kotlin.serialization)
    alias(libs.plugins.astracrypt.android.hilt)
    alias(libs.plugins.astracrypt.android.hilt.compose)
    alias(libs.plugins.astracrypt.android.work)
    alias(libs.plugins.astracrypt.android.coil)
}

val keystorePropertiesFile = rootProject.file("keystore.properties")
val keystoreProperties = Properties()
keystoreProperties.load(FileInputStream(keystorePropertiesFile))

android {
    namespace = "com.nevidimka655.astracrypt"

    defaultConfig {
        signingConfigs {
            create("release") {
                keyAlias = keystoreProperties["keyAlias"] as String
                keyPassword = keystoreProperties["keyPassword"] as String
                storeFile = file(keystoreProperties["storeFile"] as String)
                storePassword = keystoreProperties["storePassword"] as String
            }
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            isDebuggable = true
            isJniDebuggable = true
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
    implementation(projects.core.utils)
    implementation(projects.ui.resources)
    implementation(projects.core.database)
    implementation(projects.core.crypto.tink)

    implementation(projects.ui.navigation)
    implementation(projects.ui.haptic)
    implementation(projects.ui.designSystem)

    implementation(projects.features.auth.presentation)
    implementation(projects.features.calculator.presentation)

    implementation(projects.features.auth.domain)

    implementation(project(":ui:compose-details"))

    // Contracts
    implementation(projects.contract.secureContent)

    // Compose
    implementation(libs.compose.activity)

    // Other
    implementation(libs.androidx.datastore.preferences)
}