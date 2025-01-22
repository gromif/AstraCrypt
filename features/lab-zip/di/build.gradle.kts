plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.android.hilt)
}

android {
    namespace = "com.nevidimka655.di.lab_zip"
}

dependencies {
    implementation(projects.features.labZip.domain)
    implementation(projects.features.labZip.data)

    implementation(projects.core.utils)
}