rootProject.name = "AstraCrypt"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":app")

val coreModules = listOf(
    "database:app-database", "database:notes",
    "tink", "haptic", "tiles-with-coroutines",
    "resources", "utils", "design-system",
    "navigation"
)

val cryptoCoreModules = listOf("tink-datastore")

val featuresModules = listOf("help", "home")
val uiModules = listOf("compose-core", "compose-details")

val features = listOf(
    "auth",
    "files",
    "notes",
    "profile",
    "lab-zip",
    "tink-lab",
    "settings:aead"
)

// Include all modules
include(":app")
include(":features:settings:auth")
include("core", coreModules)
include("core:crypto", cryptoCoreModules)
include("features", featuresModules)
includeFeatures()
include("ui", uiModules)

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
include(":features:calculator:domain")
include(":features:calculator:presentation")
include(":core:crypto:")
