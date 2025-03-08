plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.android.library.compose)
    alias(libs.plugins.astracrypt.android.hilt)
    alias(libs.plugins.astracrypt.android.hilt.compose)
    alias(libs.plugins.astracrypt.android.room)
    alias(libs.plugins.astracrypt.kotlin.coroutines)
}

android {
    namespace = "io.gromif.astracrypt.quick_actions"
}

dependencies {
    implementation(projects.core.database)
    implementation(projects.core.crypto.tink)
    implementation(projects.core.tilesWithCoroutines)
    implementation(projects.ui.resources)
    implementation(projects.core.utils)
}