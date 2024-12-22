package com.nevidimka655.buildlogic

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {
    with(commonExtension) {
        defaultConfig.minSdk = AppConfig.MIN_SDK
        compileSdk = AppConfig.COMPILE_SDK
        buildTypes {
            release {
                isMinifyEnabled = false
            }
        }
    }
    kotlinExtension.jvmToolchain(AppConfig.KOTLIN_JVM_TOOLCHAIN_VERSION)
}