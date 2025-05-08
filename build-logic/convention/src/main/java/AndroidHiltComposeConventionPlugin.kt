import io.gromif.buildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidHiltComposeConventionPlugin: Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        dependencies {
            "implementation"(libs.findLibrary("hilt.compose.navigation").get())
            "implementation"(project(":core:utils:dispatchers"))
        }
    }
}