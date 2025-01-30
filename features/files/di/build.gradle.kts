plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.android.hilt)
    alias(libs.plugins.astracrypt.android.paging)
    alias(libs.plugins.astracrypt.android.coil)
}

android {
    namespace = "io.gromif.astracrypt.files.di"
}

dependencies {
    implementation(projects.features.files.domain)
    implementation(projects.features.files.data)

    implementation(projects.core.utils)
    implementation(projects.core.crypto.tink)

    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.documentfile)
    implementation(libs.media.exifinterface)
}