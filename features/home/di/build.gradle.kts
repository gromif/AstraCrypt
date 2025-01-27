plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.android.hilt)
    alias(libs.plugins.astracrypt.android.paging)
    alias(libs.plugins.astracrypt.android.coil)
}

android {
    namespace = "io.gromif.astracrypt.home.di"
}

dependencies {
    implementation(projects.features.home.domain)
    implementation(projects.features.home.data)

    implementation(projects.core.utils)
    implementation(projects.core.tink)

    implementation(libs.androidx.datastore.preferences)
}