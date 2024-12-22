rootProject.name = "AstraCrypt"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":app")

val coreModules = listOf(
    "haptic",
    "tiles-with-coroutines"
)
val features = listOf(
    "compose-notes"
)
val cryptoModules = listOf("tink", "tink-lab")
val uiModules = listOf(
    "compose-core",
    "compose-calculator",
    "compose-color-schemes",
    "compose-details",
    "compose-help"
)

include(group = "core", modules = coreModules)
include(group = "features", modules = features)
include(group = "crypto", modules = cryptoModules)
include(group = "ui", modules = uiModules)

//project(":$group:$it").projectDir = File("$modulesDir\\$group\\$it")
fun include(group: String, modules: List<String>) = modules.forEach { include(":$group:$it") }

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
include(":domain:notes")
include(":data:notes")
include(":di:notes")
