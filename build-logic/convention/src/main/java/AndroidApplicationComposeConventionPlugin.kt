import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import com.nevidimka655.buildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidApplicationComposeConventionPlugin: Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        with(pluginManager) {
            apply("com.android.application")
            apply("org.jetbrains.kotlin.plugin.compose")
        }

        extensions.configure<ApplicationExtension> {
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