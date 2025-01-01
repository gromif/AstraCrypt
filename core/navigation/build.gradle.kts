plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.android.library.compose)
    alias(libs.plugins.astracrypt.kotlin.coroutines)
    alias(libs.plugins.astracrypt.kotlin.serialization)
}

android {
    namespace = "com.nevidimka655.astracrypt.view.navigation"
}

dependencies {
    implementation(projects.core.resources)

    implementation(projects.features.auth.login)
    implementation(projects.features.tinkLab)
    implementation(projects.features.labZip)
    implementation(projects.features.composeNotes)
    implementation(projects.features.help)

    implementation(libs.androidx.annotation.jvm)
    implementation(libs.compose.navigation)
}