package com.nevidimka655.buildlogic

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

internal fun Project.configureDefaultConfig(
    commonExtension: CommonExtension<*,*,*,*,*,*>
): Unit = with(commonExtension) {
    defaultConfig.minSdk = AppConfig.MIN_SDK
    compileSdk = AppConfig.COMPILE_SDK
}

internal fun Project.configureKotlinAndroid() =
    kotlinExtension.jvmToolchain(AppConfig.KOTLIN_JVM_TOOLCHAIN_VERSION)