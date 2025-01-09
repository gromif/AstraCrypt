plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.android.hilt)
    alias(libs.plugins.astracrypt.android.hilt.compose)
}

android {
    namespace = "io.gromif.astracrypt.settings.aead"

}

dependencies {
    implementation(projects.domain.settings.aead)
    implementation(projects.di.settings.aead)
}