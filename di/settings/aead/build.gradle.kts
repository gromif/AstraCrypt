plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.android.hilt)
}

android {
    namespace = "io.gromif.astracrypt.settings.aead.di"
}

dependencies {
    implementation(projects.domain.settings.aead)
    implementation(projects.data.settings.aead)

    implementation(projects.domain.notes)
    implementation(projects.di.notes)

    implementation(libs.androidx.datastore.preferences)
}