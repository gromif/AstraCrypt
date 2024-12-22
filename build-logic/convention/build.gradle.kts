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
    compileOnly(libs.hilt.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidLibrary") {
            id = "astracrypt.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidApplication") {
            id = "astracrypt.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidCompose") {
            id = "astracrypt.android.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidApplicationCompose") {
            id = "astracrypt.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidHilt") {
            id = "astracrypt.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("androidRoom") {
            id = "astracrypt.android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }
        register("androidPaging") {
            id = "astracrypt.android.paging"
            implementationClass = "AndroidPagingConventionPlugin"
        }
        register("kotlinSerialization") {
            id = "astracrypt.kotlin.serialization"
            implementationClass = "KotlinSerializationConventionPlugin"
        }
        register("kotlinCoroutines") {
            id = "astracrypt.kotlin.coroutines"
            implementationClass = "KotlinCoroutinesConventionPlugin"
        }
    }
}