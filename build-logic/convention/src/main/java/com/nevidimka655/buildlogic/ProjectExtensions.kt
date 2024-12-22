package com.nevidimka655.buildlogic

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal fun Project.configureDefaultConfig(
    commonExtension: CommonExtension<*,*,*,*,*,*>
): Unit = with(commonExtension) {
    defaultConfig.minSdk = AppConfig.SDK.MIN
    compileSdk = AppConfig.SDK.COMPILE
}

internal fun Project.configureKotlinAndroid() =
    kotlinExtension.jvmToolchain(AppConfig.Kotlin.JVM_TOOLCHAIN_VERSION)