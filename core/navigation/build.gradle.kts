plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.android.library.compose)
    alias(libs.plugins.astracrypt.kotlin.coroutines)
    alias(libs.plugins.astracrypt.kotlin.serialization)
}

android {
    namespace = "com.nevidimka655.astracrypt.view.navigation"
}

dependencies {
    implementation(projects.core.resources)

    implementation(projects.features.files)
    implementation(projects.features.tinkLab)
    implementation(projects.features.labZip)
    implementation(projects.features.composeNotes)
    implementation(projects.features.help)

    implementation(projects.features.auth.settings)
    implementation(projects.features.settings.aead)

    implementation(libs.androidx.annotation.jvm)
    implementation(libs.compose.navigation)
}