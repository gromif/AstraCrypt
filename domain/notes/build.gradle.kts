plugins {
    alias(libs.plugins.astracrypt.android.library)
}

android {
    namespace = "com.nevidimka655.domain.notes"
}

dependencies {
    // Paging
    api(libs.paging.compose)
    api(libs.paging.runtime.ktx)
}