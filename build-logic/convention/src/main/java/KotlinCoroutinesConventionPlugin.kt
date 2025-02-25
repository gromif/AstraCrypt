import io.gromif.buildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class KotlinCoroutinesConventionPlugin: Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        dependencies {
            "implementation"(libs.findLibrary("kotlin.coroutines").get())
            "implementation"(project(":core:utils:dispatchers"))
        }
    }
}