plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.android.hilt)
}

android {
    namespace = "io.gromif.astracrypt.settings.aead.di"
}

dependencies {
    implementation(projects.features.settings.aead.domain)
    implementation(projects.features.settings.aead.data)

    implementation(projects.features.notes.domain)
    implementation(projects.features.notes.di)

    implementation(libs.androidx.datastore.preferences)
}