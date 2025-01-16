plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.android.hilt)
    alias(libs.plugins.astracrypt.android.paging)
    alias(libs.plugins.astracrypt.android.coil)
}

android {
    namespace = "io.gromif.astracrypt.files.di"
}

dependencies {
    implementation(projects.core.database.files)
    implementation(projects.domain.files)
    implementation(projects.data.files)

    implementation(projects.core.utils)
    implementation(projects.core.tink)
}