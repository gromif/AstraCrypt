plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "io.gromif.ui.compose.core"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")

        buildFeatures.compose = true
    }
}

dependencies {
    api(projects.ui.haptic)
    api(platform(libs.compose.bom))
    api(libs.compose.material3.icons.core)
    api(libs.compose.material3.icons.extended)
    api(libs.compose.material3)
    api(libs.compose.material3.windowSizeClass)

    api(libs.compose.ui.tooling)
    debugApi(libs.compose.ui.test.manifest)
}