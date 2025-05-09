package io.gromif.buildlogic

internal object AppConfig {

    object SDK {
        const val TARGET = 36
        const val COMPILE = TARGET
        const val MIN = 23
    }

    object Kotlin {
        const val JVM_TOOLCHAIN_VERSION = 21
    }

    object Versions {
        private const val MAJOR = 2
        private const val MINOR = 2
        private const val PATCH = 0

        const val CODE = MAJOR * 10000 + MINOR * 100 + PATCH
        const val NAME = "$MAJOR.$MINOR.$PATCH"

        const val BUILD_TOOLS = "36.0.0"
    }

    object Tools {
        const val COMPOSE_METRICS = true
        const val LEAK_CANARY = true
    }

}