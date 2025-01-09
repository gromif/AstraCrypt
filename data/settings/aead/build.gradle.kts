plugins {
    alias(libs.plugins.astracrypt.android.library)
}

android {
    namespace = "io.gromif.astracrypt.settings.aead.data"

}

dependencies {
    implementation(projects.domain.settings.aead)
}