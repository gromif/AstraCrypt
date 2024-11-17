@file:Suppress("UnstableApiUsage")

import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.navigation.safeargs)
    alias(libs.plugins.compose.compiler)
}

// Create a variable called keystorePropertiesFile, and initialize it to your
// keystore.properties file, in the rootProject folder.
val keystorePropertiesFile = rootProject.file("keystore.properties")

// Initialize a new Properties() object called keystoreProperties.
val keystoreProperties = Properties()

// Load your keystore.properties file into the keystoreProperties object.
keystoreProperties.load(FileInputStream(keystorePropertiesFile))

android {
    compileSdk = project.property("compileSdk").toString().toInt()
    buildToolsVersion = project.property("buildTools").toString()

    defaultConfig {
        applicationId = project.property("applicationId").toString()
        minSdk = project.property("minSdk").toString().toInt()
        targetSdk = project.property("targetSdk").toString().toInt()
        versionCode = getVersionCode()
        versionName = getVersionName()
        vectorDrawables.useSupportLibrary = true
        buildFeatures.run {
            compose = true
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
        kotlinOptions.jvmTarget = "17"
        java {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    namespace = project.property("applicationId").toString()
}

dependencies {
    implementation(project(mapOf("path" to ":ui:compose-core")))
    implementation(project(mapOf("path" to ":ui:compose-calculator")))
    implementation(project(mapOf("path" to ":ui:compose-color-schemes")))
    implementation(project(mapOf("path" to ":ui:compose-details")))
    implementation(project(mapOf("path" to ":ui:compose-help")))
    implementation(project(mapOf("path" to ":ui:compose-notes")))
    implementation(project(mapOf("path" to ":core:haptic")))
    implementation(project(mapOf("path" to ":core:tiles-with-coroutines")))
    implementation(project(mapOf("path" to ":crypto:tink")))

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
    implementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)

    // Paging
    implementation(libs.paging.compose)
    implementation(libs.paging.runtime.ktx)

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.room.paging)
    ksp(libs.room.compiler)

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
    implementation(libs.google.android.material)
    implementation(libs.google.crypto.tink)

    // DI - Hilt
    ksp(libs.hilt.android.compiler)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.android)
    implementation(libs.hilt.work)

    // Kotlin
    implementation(libs.kotlin.coroutines)
    implementation(libs.kotlin.serialization)

    // Other
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
}

fun getVersionCode() = project.property("versionMajor").toString().toInt() * 10000 +
        project.property("versionMinor").toString().toInt() * 100 +
        project.property("versionPatch").toString().toInt()

fun getVersionName() = buildString {
    append(project.property("versionMajor"))
    append(".")
    append(project.property("versionMinor"))
    append(".")
    append(project.property("versionPatch"))
}
