plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.android.hilt)
}

android {
    namespace = "io.gromif.astracrypt.device_admin.di"
}

dependencies {
    implementation(projects.features.deviceAdmin.domain)
    implementation(projects.features.deviceAdmin.data)

    implementation(projects.core.deviceAdminApi)
}
