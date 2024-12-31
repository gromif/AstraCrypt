plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.kotlin.coroutines)
    alias(libs.plugins.astracrypt.kotlin.serialization)
}

android {
    namespace = "com.nevidimka655.astracrypt.auth.data"
}

dependencies {
    implementation(projects.domain.auth)

    implementation(projects.core.tink)
    implementation(libs.androidx.datastore.preferences)
}