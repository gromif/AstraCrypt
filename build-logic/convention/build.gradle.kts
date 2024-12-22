plugins {
    `kotlin-dsl`
}

group = "com.nevidimka655.buildlogic"

kotlin {
    jvmToolchain(21)
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidLibrary") {
            id = "astracrypt.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
    }
}