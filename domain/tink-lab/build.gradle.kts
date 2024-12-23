plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.kotlin.serialization)
}

android {
    namespace = "com.nevidimka655.tink_lab.domain"
}

dependencies {
    implementation(projects.core.tink)
}