plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.android.hilt)
}

android {
    namespace = "com.nevidimka655.notes.di"
}

dependencies {
    implementation(projects.features.notes.domain)
    implementation(projects.features.notes.data)

    implementation(projects.core.database.notes)
    implementation(projects.core.utils)
    implementation(libs.androidx.datastore.preferences)
}