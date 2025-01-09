plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.kotlin.coroutines)
}

android {
    namespace = "io.gromif.astracrypt.settings.aead.data"

}

dependencies {
    implementation(projects.domain.settings.aead)

    implementation(projects.core.tink)
}