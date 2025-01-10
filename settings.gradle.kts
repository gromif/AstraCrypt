rootProject.name = "AstraCrypt"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":app")

val coreModules = listOf(
    "database:app-database", "database:files", "database:notes",
    "tink", "haptic", "tiles-with-coroutines",
    "resources", "utils", "design-system",
    "navigation"
)
val featuresModules = listOf(
    "auth:login", "auth:settings",
    "calculator",
    "compose-notes",
    "tink-lab",
    "help",
    "lab-zip",
    "settings:aead"
)
val uiModules = listOf("compose-core", "compose-details")
val domainModules = listOf(
    "auth",
    "files",
    "calculator", "notes", "tink-lab", "lab-zip",
    "settings:aead"
)
val dataModules = listOf(
    "auth", "notes", "tink-lab", "lab-zip",
    "settings:aead"
)
val diModules = listOf(
    "auth", "notes", "tink-lab", "lab-zip", "utils",
    "settings:aead"
)

// Include all modules
include(":app")
include("core", coreModules)
include("features", featuresModules)
include("ui", uiModules)
include("domain", domainModules)
include("data", dataModules)
include("di", diModules)

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
