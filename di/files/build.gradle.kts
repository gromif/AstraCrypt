plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.android.hilt)
    alias(libs.plugins.astracrypt.android.paging)
}

android {
    namespace = "io.gromif.astracrypt.files.di"
}

dependencies {
    implementation(projects.domain.files)
    implementation(projects.data.files)
}