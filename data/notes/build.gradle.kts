plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.android.room)
    alias(libs.plugins.astracrypt.android.paging)
}

android {
    namespace = "com.nevidimka655.notes.data"
}

dependencies {
    implementation(projects.domain.notes)
}