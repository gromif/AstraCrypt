package io.gromif.buildlogic

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
        private const val MAJOR = 2
        private const val MINOR = 0
        private const val PATCH = 0

        const val CODE = MAJOR * 10000 + MINOR * 100 + PATCH
        const val NAME = "$MAJOR.$MINOR.$PATCH"

        const val BUILD_TOOLS = "35.0.0"
    }

    object Compose {
        const val METRICS = true
    }

}