rootProject.name = "AstraCrypt"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":app")

val coreModules = listOf(
    "database:app-database", "tiles-with-coroutines",
    "resources", "utils", "design-system",
    "navigation"
)

val cryptoCoreModules = listOf("tink", "tink-datastore")

val featuresModules = listOf("help")
val uiModules = listOf(
    "compose-core", "compose-details", "haptic"
)

val features = listOf(
    "auth",
    "files",
    "notes",
    "profile",
    "lab-zip",
    "tink-lab",
)

// Include all modules
include(":app")
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
include(":features:about")
include(":features:about:google-play")
include(":features:about:fdroid")
include(":features:about:privacy")
include(":features:quick-actions")
include(":features:device-admin")
