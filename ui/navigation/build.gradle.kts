plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.android.library.flavor.market)
    alias(libs.plugins.astracrypt.android.library.compose)
    alias(libs.plugins.astracrypt.kotlin.coroutines)
    alias(libs.plugins.astracrypt.kotlin.serialization)
}

android {
    namespace = "io.gromif.astracrypt.presentation.navigation"
}

dependencies {
    implementation(projects.ui.resources)

    implementation(projects.features.files.presentation.details)
    implementation(projects.features.files.presentation.export)
    implementation(projects.features.files.presentation.files)
    implementation(projects.features.files.presentation.recent)
    implementation(projects.features.files.presentation.settings)
    implementation(projects.features.tinkLab.presentation)
    implementation(projects.features.labZip.presentation)
    implementation(projects.features.notes.presentation)
    implementation(projects.features.auth.presentation)
    implementation(projects.features.help)
    implementation(projects.features.profile.presentation)
    implementation(projects.features.quickActions)
    implementation(projects.features.deviceAdmin.presentation)
    implementation(projects.features.security.secureContent)

    implementation(projects.features.donate)

    defaultImplementation(projects.features.about.fdroid)
    playImplementation(projects.features.about.googlePlay)
    implementation(projects.features.about.privacy)

    implementation(libs.compose.navigation)
}