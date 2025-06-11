plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.android.library.compose)
    alias(libs.plugins.astracrypt.kotlin.coroutines)
    alias(libs.plugins.astracrypt.android.paging)

    alias(libs.plugins.astracrypt.test.unit)
}

android {
    namespace = "io.gromif.astracrypt.files.shared"
}

dependencies {
    implementation(projects.features.files.domain)

    implementation(projects.ui.resources)
    implementation(projects.core.utils)
    implementation(projects.ui.designSystem)
}
