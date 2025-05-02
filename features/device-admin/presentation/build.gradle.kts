plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.android.library.compose)
    alias(libs.plugins.astracrypt.android.hilt)
    alias(libs.plugins.astracrypt.android.hilt.compose)
    alias(libs.plugins.astracrypt.kotlin.coroutines)

    alias(libs.plugins.astracrypt.test.unit)
}

android {
    namespace = "io.gromif.astracrypt.device_admin.presentation"
}

dependencies {
    implementation(projects.features.deviceAdmin.domain)
    implementation(projects.features.deviceAdmin.di)

    implementation(projects.ui.resources)
}
