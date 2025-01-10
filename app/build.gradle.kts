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
    implementation(projects.core.navigation)
    implementation(projects.core.designSystem)
    implementation(projects.core.utils)
    implementation(projects.core.resources)
    implementation(projects.core.haptic)
    implementation(projects.core.tilesWithCoroutines)

    implementation(projects.core.database.appDatabase)
    implementation(projects.core.database.files)

    implementation(projects.core.tink)

    implementation(projects.features.auth.login)
    implementation(projects.features.calculator)

    implementation(projects.domain.auth)
    implementation(projects.domain.calculator)
    implementation(projects.di.auth)

    implementation(projects.di.utils)

    implementation(project(":ui:compose-details"))

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

    // Media
    implementation(libs.media.exifinterface)

    // Other
    implementation(libs.androidx.documentfile)
    implementation(libs.androidx.datastore.preferences)
}