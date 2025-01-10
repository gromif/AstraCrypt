plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.kotlin.coroutines)
    alias(libs.plugins.astracrypt.kotlin.serialization)
    alias(libs.plugins.astracrypt.android.paging)
}

android {
    namespace = "io.gromif.astracrypt.files.data"
}

dependencies {
    implementation(projects.domain.files)

    implementation(projects.core.tink)

    implementation(libs.androidx.datastore.preferences)
}