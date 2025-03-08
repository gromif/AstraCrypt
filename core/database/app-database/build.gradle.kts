plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.android.hilt)
    alias(libs.plugins.astracrypt.android.room)
}

android {
    namespace = "com.nevidimka655.astracrypt.data.db"

    defaultConfig {
        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }
}

dependencies {
    implementation(projects.features.files.domain)
    implementation(projects.features.files.data)
    implementation(projects.features.notes.data)
}