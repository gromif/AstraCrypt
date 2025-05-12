plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.android.room)
    alias(libs.plugins.astracrypt.android.paging)
}

android {
    namespace = "io.gromif.notes.data"
}

dependencies {
    implementation(projects.features.notes.domain)

    implementation(projects.core.crypto.tink)
    implementation(projects.core.utils)
    implementation(libs.androidx.datastore.preferences)
}