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

    implementation(projects.core.crypto.tinkDatastore)
    implementation(projects.core.utils)
    implementation(projects.core.crypto.tink)
}