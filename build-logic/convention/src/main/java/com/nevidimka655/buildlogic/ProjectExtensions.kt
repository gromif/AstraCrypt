package com.nevidimka655.buildlogic

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.nevidimka655.buildlogic.flavor.createMarket
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal fun Project.configureDefaultConfig(
    commonExtension: CommonExtension<*,*,*,*,*,*>
): Unit = with(commonExtension) {
    defaultConfig.minSdk = AppConfig.SDK.MIN
    compileSdk = AppConfig.SDK.COMPILE
}

internal fun ApplicationExtension.configureFlavors() {
    Flavor.createMarket(this)
}

@Suppress("KotlinConstantConditions")
internal fun Project.configureComposeMetrics() {
    if (AppConfig.Compose.METRICS) extensions.configure<ComposeCompilerGradlePluginExtension> {
        reportsDestination.set(layout.buildDirectory.dir("compose_compiler"))
        metricsDestination.set(layout.buildDirectory.dir("compose_compiler"))
    }
}

internal fun Project.configureComposeStabilityConfig() {
    extensions.configure<ComposeCompilerGradlePluginExtension> {
        val config = rootProject.layout.projectDirectory.file(
            "build-logic/compose-stability-config.conf"
        ).asFile
        stabilityConfigurationFiles.add { config }
    }
}

internal fun Project.configureKotlinAndroid() =
    kotlinExtension.jvmToolchain(AppConfig.Kotlin.JVM_TOOLCHAIN_VERSION)