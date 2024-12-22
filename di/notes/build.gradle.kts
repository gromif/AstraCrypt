plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.android.hilt)
}

android {
    namespace = "com.nevidimka655.notes.di"
}

dependencies {
    implementation(projects.domain.notes)
    implementation(projects.data.notes)
}