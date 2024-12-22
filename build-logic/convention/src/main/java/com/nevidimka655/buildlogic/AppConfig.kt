package com.nevidimka655.buildlogic

internal object AppConfig {

    object SDK {
        const val TARGET = 35
        const val COMPILE = TARGET
        const val MIN = 23
    }

    object Kotlin {
        const val JVM_TOOLCHAIN_VERSION = 21
    }

    object Versions {
        private const val MAJOR = 1
        private const val MINOR = 7
        private const val PATCH = 3

        const val CODE = MAJOR * 10000 + MINOR * 100 + PATCH
        const val NAME = "$MAJOR.$MINOR.$PATCH"
    }

}