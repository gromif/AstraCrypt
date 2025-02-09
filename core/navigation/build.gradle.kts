plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.android.library.flavor.market)
    alias(libs.plugins.astracrypt.android.library.compose)
    alias(libs.plugins.astracrypt.kotlin.coroutines)
    alias(libs.plugins.astracrypt.kotlin.serialization)
}

android {
    namespace = "com.nevidimka655.astracrypt.view.navigation"
}

dependencies {
    implementation(projects.core.resources)

    implementation(projects.features.files.presentation)
    implementation(projects.features.tinkLab.presentation)
    implementation(projects.features.labZip.presentation)
    implementation(projects.features.notes.presentation)
    implementation(projects.features.help)
    implementation(projects.features.home)
    implementation(projects.features.profile.presentation)

    fdroidImplementation(projects.features.settings.about.fdroid)
    playImplementation(projects.features.settings.about.googlePlay)

    implementation(projects.features.settings.auth)
    implementation(projects.features.settings.aead.presentation)

    implementation(libs.compose.navigation)
}