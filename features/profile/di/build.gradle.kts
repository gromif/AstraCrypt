plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.android.hilt)
    alias(libs.plugins.astracrypt.android.coil)
}

android {
    namespace = "io.gromif.astracrypt.profile.di"
}

dependencies {
    implementation(projects.features.profile.domain)
    implementation(projects.features.profile.data)

    implementation(projects.core.utils)
    implementation(projects.core.crypto.tink)
    implementation(projects.core.crypto.tinkDatastore)
}