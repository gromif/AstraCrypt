plugins {
    alias(libs.plugins.astracrypt.android.library)
}

android {
    namespace = "io.gromif.astracrypt.settings.aead.di"
}

dependencies {
    implementation(projects.domain.settings.aead)
    implementation(projects.data.settings.aead)
}