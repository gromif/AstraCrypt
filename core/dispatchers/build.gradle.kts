plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.android.hilt)
}

android {
    namespace = "io.gromif.astracrypt.utils.dispatchers"
}

dependencies {
    implementation(libs.kotlin.coroutines)
}