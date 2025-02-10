
import com.android.build.api.dsl.LibraryExtension
import com.nevidimka655.buildlogic.configureComposeMetrics
import com.nevidimka655.buildlogic.configureComposeStabilityConfig
import com.nevidimka655.buildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidLibraryComposeConventionPlugin: Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        with(pluginManager) {
            apply("com.android.library")
            apply("org.jetbrains.kotlin.plugin.compose")
        }
        configureComposeStabilityConfig()
        configureComposeMetrics()
        extensions.configure<LibraryExtension> {
            with(defaultConfig) {
                buildFeatures.compose = true
            }

            dependencies {
                add("implementation", project(":ui:compose-core"))
                "implementation"(libs.findLibrary("lifecycle.runtime.compose").get())
            }
        }
    }
}