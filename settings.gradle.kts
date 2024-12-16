rootProject.name = "AstraCrypt"

include(":app")

val coreModules = listOf(
    "haptic",
    "tiles-with-coroutines"
)
val cryptoModules = listOf("tink", "tink-lab")
val uiModules = listOf(
    "compose-core",
    "compose-calculator",
    "compose-color-schemes",
    "compose-details",
    "compose-help",
    "compose-notes"
)

include(group = "core", modules = coreModules)
include(group = "crypto", modules = cryptoModules)
include(group = "ui", modules = uiModules)

//project(":$group:$it").projectDir = File("$modulesDir\\$group\\$it")
fun include(group: String, modules: List<String>) = modules.forEach { include(":$group:$it") }

pluginManagement {
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
        maven {
            setUrl("https://swisscodemonkeys.github.io/appbrain-sdk/maven")
        }
    }
}
