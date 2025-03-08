plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.android.hilt)
}

android {
    namespace = "io.gromif.lab_zip.di"
}

dependencies {
    implementation(projects.features.labZip.domain)
    implementation(projects.features.labZip.data)

    implementation(projects.core.utils)
}