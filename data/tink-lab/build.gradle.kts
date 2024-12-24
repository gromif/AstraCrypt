plugins {
    alias(libs.plugins.astracrypt.android.library)
}

android {
    namespace = "com.nevidimka655.tink_lab.data"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(projects.domain.tinkLab)

    implementation(projects.core.tink)
}