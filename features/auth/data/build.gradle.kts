plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.kotlin.coroutines)
    alias(libs.plugins.astracrypt.kotlin.serialization)
}

android {
    namespace = "io.gromif.astracrypt.auth.data"
}

dependencies {
    implementation(projects.features.auth.domain)

    implementation(projects.core.utils)
    implementation(projects.core.crypto.tink)
    implementation(projects.core.crypto.tinkDatastore)
}