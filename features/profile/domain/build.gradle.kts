plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.kotlin.coroutines)
}

android {
    namespace = "io.gromif.astracrypt.profile.domain"
}