plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.android.hilt)
}

android {
    namespace = "com.nevidimka655.astracrypt.auth.di"
}

dependencies {
    implementation(projects.domain.auth)
    implementation(projects.data.auth)

    implementation(projects.core.utils)
    implementation(projects.core.tink)
}