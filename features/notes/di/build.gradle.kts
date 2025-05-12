plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.android.hilt)
    alias(libs.plugins.astracrypt.android.paging)
}

android {
    namespace = "io.gromif.notes.di"
}

dependencies {
    implementation(projects.features.notes.domain)
    implementation(projects.features.notes.data)

    implementation(projects.core.utils)
    implementation(projects.core.crypto.tink)
    implementation(libs.androidx.datastore.preferences)
}