plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.android.hilt)
}

android {
    namespace = "io.gromif.astracrypt.container.di"
}

dependencies {
    implementation(projects.features.container.domain)
    implementation(projects.features.container.data)

}
