plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.android.room)
    alias(libs.plugins.astracrypt.android.paging)
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
    implementation(projects.core.database.files)
    implementation(projects.core.database.notes)
}