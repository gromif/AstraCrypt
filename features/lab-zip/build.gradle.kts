plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.android.library.compose)
    alias(libs.plugins.astracrypt.android.hilt)
    alias(libs.plugins.astracrypt.android.hilt.compose)
    alias(libs.plugins.astracrypt.kotlin.coroutines)
    alias(libs.plugins.astracrypt.android.work)
}

android {
    namespace = "com.nevidimka655.features.lab_zip"
}

dependencies {
    implementation(projects.domain.labZip)
    implementation(projects.di.labZip)

    implementation(libs.androidx.documentfile)

    implementation(projects.core.resources)
    implementation(projects.di.core)
    implementation(projects.core.utils)
    implementation(projects.core.tink)
}