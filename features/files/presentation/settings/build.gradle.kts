plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.android.library.compose)
    alias(libs.plugins.astracrypt.android.hilt)
    alias(libs.plugins.astracrypt.android.hilt.compose)
    alias(libs.plugins.astracrypt.android.work)

    alias(libs.plugins.astracrypt.kotlin.coroutines)
    alias(libs.plugins.astracrypt.kotlin.serialization)
    alias(libs.plugins.astracrypt.test.unit)
}

android {
    namespace = "io.gromif.astracrypt.files.settings"
}

dependencies {
    implementation(projects.features.files.domain)
    implementation(projects.features.files.di)

    implementation(projects.ui.resources)

    implementation(projects.core.crypto.tink)
    implementation(projects.core.utils)
}
