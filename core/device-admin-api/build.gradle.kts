plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.kotlin.coroutines)
    alias(libs.plugins.astracrypt.android.hilt)

    alias(libs.plugins.astracrypt.test.unit)
}

android {
    namespace = "io.gromif.device_admin_api"
}

dependencies {
    implementation(projects.ui.resources)
}