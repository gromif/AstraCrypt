plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.android.library.compose)
}

android {
    namespace = "io.gromif.astracrypt.settings.about"
}

dependencies {
    implementation(projects.features.about)
    implementation(projects.ui.resources)
}