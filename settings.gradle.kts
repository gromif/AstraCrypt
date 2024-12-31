rootProject.name = "AstraCrypt"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":app")

val coreModules = listOf(
    "database:notes",
    "tink", "haptic", "tiles-with-coroutines",
    "resources", "utils", "design-system",
    "navigation"
)
val featuresModules = listOf("compose-notes", "tink-lab", "help", "lab-zip")
val uiModules = listOf("compose-core", "compose-calculator", "compose-color-schemes", "compose-details")
val domainModules = listOf("notes", "tink-lab", "lab-zip")
val dataModules = listOf("notes", "tink-lab", "lab-zip")
val diModules = listOf("notes", "tink-lab", "lab-zip", "utils")

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
include(":core:database:files")
