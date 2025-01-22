plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.android.hilt)
}

android {
    namespace = "com.nevidimka655.astracrypt.auth.di"
}

dependencies {
    implementation(projects.features.auth.domain)
    implementation(projects.features.auth.data)

    implementation(libs.androidx.datastore.preferences)
    implementation(projects.core.utils)
    implementation(projects.core.tink)
}