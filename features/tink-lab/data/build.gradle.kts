plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.kotlin.coroutines)
    alias(libs.plugins.astracrypt.kotlin.serialization)
}

android {
    namespace = "io.gromif.tinkLab.data"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(projects.features.tinkLab.domain)

    implementation(projects.core.utils)
    implementation(projects.core.crypto.tink)

    implementation(libs.androidx.core.ktx)
}