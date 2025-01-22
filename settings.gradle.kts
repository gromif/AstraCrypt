rootProject.name = "AstraCrypt"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":app")

val coreModules = listOf(
    "database:app-database", "database:notes",
    "tink", "haptic", "tiles-with-coroutines",
    "resources", "utils", "design-system",
    "navigation"
)
val featuresModules = listOf("calculator", "help",)
val uiModules = listOf("compose-core", "compose-details")
val domainModules = listOf("calculator")

val features = listOf(
    "auth",
    "files",
    "notes",
    "lab-zip",
    "tink-lab",
    "settings:aead"
)

// Include all modules
include(":app")
include(":features:settings:auth")
include("core", coreModules)
include("features", featuresModules)
includeFeatures()
include("ui", uiModules)
include("domain", domainModules)

fun include(group: String, modules: List<String>) = modules.forEach { include(":$group:$it") }
fun includeFeatures() = features.forEach {
    include(":features:$it:domain")
    include(":features:$it:data")
    include(":features:$it:presentation")
    include(":features:$it:di")
}

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
include(":core:utils:dispatchers")

include(":features:calculator:presentation")
include(":features:calculator:domain")
