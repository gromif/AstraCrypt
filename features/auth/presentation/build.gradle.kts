plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.android.library.compose)
    alias(libs.plugins.astracrypt.android.hilt)
    alias(libs.plugins.astracrypt.android.hilt.compose)

    alias(libs.plugins.astracrypt.kotlin.coroutines)

    alias(libs.plugins.astracrypt.test.unit)
}

android {
    namespace = "io.gromif.astracrypt.auth.presentation"
}

dependencies {
    implementation(projects.contract.auth)

    implementation(projects.features.auth.domain)
    implementation(projects.features.auth.di)
    implementation(projects.features.calculator.presentation)

    implementation(projects.core.crypto.tink)
    implementation(projects.core.utils)
    implementation(projects.ui.resources)
}