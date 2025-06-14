plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.android.library.compose)
}

android {
    namespace = "io.gromif.compose.details"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}