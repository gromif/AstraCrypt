@file:Suppress("NOTHING_TO_INLINE")

plugins {
    `kotlin-dsl`
}

group = "io.gromif.buildlogic"

kotlin {
    jvmToolchain(21)
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.hilt.gradlePlugin)
    compileOnly(libs.compose.compiler.gradlePlugin)
    compileOnly(libs.detekt.gradlePlugin)
}

gradlePlugin {
    plugins {
        flavors()
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
        register("androidHiltCompose") {
            id = "astracrypt.android.hilt.compose"
            implementationClass = "AndroidHiltComposeConventionPlugin"
        }
        register("androidRoom") {
            id = "astracrypt.android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }
        register("androidPaging") {
            id = "astracrypt.android.paging"
            implementationClass = "AndroidPagingConventionPlugin"
        }
        register("androidCoil") {
            id = "astracrypt.android.coil"
            implementationClass = "AndroidCoilConventionPlugin"
        }
        register("androidWork") {
            id = "astracrypt.android.work"
            implementationClass = "AndroidWorkConventionPlugin"
        }
        register("kotlinSerialization") {
            id = "astracrypt.kotlin.serialization"
            implementationClass = "KotlinSerializationConventionPlugin"
        }
        register("kotlinParcelize") {
            id = "astracrypt.kotlin.parcelize"
            implementationClass = "KotlinParcelizeConventionPlugin"
        }
        register("kotlinCoroutines") {
            id = "astracrypt.kotlin.coroutines"
            implementationClass = "KotlinCoroutinesConventionPlugin"
        }
        register("testUnit") {
            id = "astracrypt.test.unit"
            implementationClass = "TestUnitConventionPlugin"
        }
    }
}

private inline fun NamedDomainObjectContainer<PluginDeclaration>.flavors() {
    register("flavorMarket") {
        id = "astracrypt.flavor.market"
        implementationClass = "io.gromif.buildlogic.flavor.FlavorMarketConventionPlugin"
    }
}