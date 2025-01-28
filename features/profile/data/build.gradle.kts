plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.kotlin.coroutines)
    alias(libs.plugins.astracrypt.kotlin.serialization)
    alias(libs.plugins.astracrypt.android.coil)
}

android {
    namespace = "io.gromif.astracrypt.profile.data"
}

dependencies {
    implementation(projects.features.profile.domain)

    implementation(projects.core.tink)
    implementation(projects.core.utils)

    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.documentfile)
}