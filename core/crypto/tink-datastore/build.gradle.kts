plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.kotlin.coroutines)
}

android {
    namespace = "io.gromif.tink_datastore"
}

dependencies {
    implementation(projects.core.crypto.tink)
    api(libs.androidx.datastore.preferences)
}