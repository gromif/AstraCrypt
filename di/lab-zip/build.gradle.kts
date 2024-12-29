plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.android.hilt)
}

android {
    namespace = "com.nevidimka655.di.lab_zip"
}

dependencies {
    implementation(projects.domain.labZip)
    implementation(projects.data.labZip)
    implementation(projects.core.utils)
}