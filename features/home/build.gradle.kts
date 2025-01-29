plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.android.library.compose)
    alias(libs.plugins.astracrypt.android.hilt)
    alias(libs.plugins.astracrypt.android.hilt.compose)
}

android {
    namespace = "io.gromif.astracrypt.home.presentation"
}

dependencies {
    implementation(projects.features.profile.presentation)
    implementation(projects.features.files.presentation)

    implementation(projects.core.resources)
}