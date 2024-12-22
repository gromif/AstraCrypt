package com.nevidimka655.buildlogic

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

internal fun Project.configureDefaultConfig(
    commonExtension: CommonExtension<*,*,*,*,*,*>
): Unit = with(commonExtension) {
    defaultConfig.minSdk = AppConfig.SDK.MIN
    compileSdk = AppConfig.SDK.COMPILE
}

internal fun Project.configureKotlinAndroid() =
    kotlinExtension.jvmToolchain(AppConfig.Kotlin.JVM_TOOLCHAIN_VERSION)