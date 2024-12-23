plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.android.hilt)
}

android {
    namespace = "com.nevidimka655.tink_lab.di"
}

dependencies {
    implementation(projects.domain.tinkLab)
    implementation(projects.core.tink)
}