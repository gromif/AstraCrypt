plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.android.library.compose)
    alias(libs.plugins.astracrypt.android.hilt)
    alias(libs.plugins.astracrypt.android.hilt.compose)
    alias(libs.plugins.astracrypt.kotlin.coroutines)
    alias(libs.plugins.astracrypt.test.unit)
}

android {
    namespace = "io.gromif.secure_content"
}

dependencies {
    implementation(projects.contract.secureContent)

    implementation(projects.ui.resources)
    implementation(projects.core.utils)
    implementation(libs.androidx.datastore.preferences)
}
