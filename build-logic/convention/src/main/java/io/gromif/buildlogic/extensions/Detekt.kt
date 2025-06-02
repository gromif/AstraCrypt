package io.gromif.buildlogic.extensions

import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import io.gromif.buildlogic.Plugins
import io.gromif.buildlogic.libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

@Suppress("DEPRECATION")
internal fun Project.configureDetekt() {
    pluginManager.apply(Plugins.DETEKT)

    extensions.configure<DetektExtension> {
        buildUponDefaultConfig = true
        config.setFrom(rootProject.file("config/detekt.yml"))
        autoCorrect = true
        reports {
            html.required.set(true)
            sarif.required.set(true)
        }
    }

    dependencies {
        "detektPlugins"(libs.findLibrary("detekt.formatting").get())
    }
}