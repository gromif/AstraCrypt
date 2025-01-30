plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.kotlin.coroutines)
}

android {
    namespace = "io.gromif.astracrypt.settings.aead.data"

}

dependencies {
    implementation(projects.features.settings.aead.domain)

    implementation(projects.features.notes.domain)

    implementation(projects.core.crypto.tink)

    implementation(libs.androidx.datastore.preferences)
}