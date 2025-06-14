plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.test.unit)
    alias(libs.plugins.astracrypt.kotlin.coroutines)
    alias(libs.plugins.astracrypt.kotlin.serialization)
}

android {
    namespace = "io.gromif.crypto.tink"
}

dependencies {
    api(libs.google.crypto.tink) {
        exclude(group = "androidx.annotation")
    }
}