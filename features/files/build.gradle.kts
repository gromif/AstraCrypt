plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.android.library.compose)
    alias(libs.plugins.astracrypt.kotlin.coroutines)
    alias(libs.plugins.astracrypt.android.paging)
    alias(libs.plugins.astracrypt.android.work)
}

android {
    namespace = "io.gromif.astracrypt.files"
}

dependencies {
    implementation(projects.domain.files)
    implementation(projects.di.files)

    implementation(projects.core.resources)
    implementation(projects.core.utils)
    implementation(projects.di.utils)
}