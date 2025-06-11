rootProject.name = "AstraCrypt"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":app")

val contractGroup = listOf("auth", "secure-content")

val coreModules = listOf(
    "database", "dispatchers", "tiles-with-coroutines",
    "utils", "device-admin-api"
)

val cryptoCoreModules = listOf("tink", "tink-datastore")

val featuresModules = listOf(
    "help",
    "security:secure-content"
)
val uiModules = listOf(
    "compose-core", "compose-details", "design-system",
    "haptic", "navigation", "resources",
)

val features = listOf(
    "auth",
    "device-admin",
    "files",
    "notes",
    "profile",
    "lab-zip",
    "tink-lab",
)

val filesModules = listOf(
    "domain",
    "data",
    "di",
    "presentation",
    "presentation:shared",
)

// Include all modules
include(":app")
include("core", coreModules)
include("contract", contractGroup)
include("core:crypto", cryptoCoreModules)
include("features", featuresModules)
include("features:files", filesModules)
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
include(":features:calculator:domain")
include(":features:calculator:presentation")
include(":core:crypto:")
include(":features:about")
include(":features:about:google-play")
include(":features:about:fdroid")
include(":features:about:privacy")
include(":features:quick-actions")
include(":features:donate")
