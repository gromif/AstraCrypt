plugins {
    alias(libs.plugins.astracrypt.android.library)
}

android {
    namespace = "io.gromif.astracrypt.settings.aead"

}

dependencies {
    implementation(projects.domain.settings.aead)
    implementation(projects.di.settings.aead)
}