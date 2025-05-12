plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.android.library.compose)
}

android {
    namespace = "io.gromif.calculator"
}

dependencies {
    implementation(projects.features.calculator.domain)

    implementation(libs.lifecycle.viewmodel.compose)
}