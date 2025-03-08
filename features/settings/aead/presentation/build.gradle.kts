plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.android.library.compose)
    alias(libs.plugins.astracrypt.android.hilt)
    alias(libs.plugins.astracrypt.android.hilt.compose)
    alias(libs.plugins.astracrypt.kotlin.coroutines)
}

android {
    namespace = "io.gromif.astracrypt.settings.aead"

}

dependencies {
    implementation(projects.features.settings.aead.domain)
    implementation(projects.features.settings.aead.di)
    implementation(projects.features.files.presentation)
    implementation(projects.features.notes.presentation)
    implementation(projects.features.auth.presentation)
    implementation(projects.features.profile.presentation)

    implementation(projects.core.resources)
    implementation(projects.core.crypto.tink)
}