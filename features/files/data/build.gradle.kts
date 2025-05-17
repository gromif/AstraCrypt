plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.kotlin.coroutines)
    alias(libs.plugins.astracrypt.kotlin.serialization)
    alias(libs.plugins.astracrypt.android.room)
    alias(libs.plugins.astracrypt.android.paging)
    alias(libs.plugins.astracrypt.android.coil)
    alias(libs.plugins.astracrypt.test.unit)
}

android {
    namespace = "io.gromif.astracrypt.files.data"
}

dependencies {
    implementation(projects.features.files.domain)

    implementation(projects.core.crypto.tink)
    implementation(projects.core.utils)

    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.documentfile)
    implementation(libs.media.exifinterface)
}