plugins {
    alias(libs.plugins.astracrypt.android.library)
    alias(libs.plugins.astracrypt.kotlin.coroutines)

    alias(libs.plugins.astracrypt.test.unit)
}

android {
    namespace = "io.gromif.astracrypt.device_admin.domain"
}
