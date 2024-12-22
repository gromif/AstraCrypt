import com.nevidimka655.buildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class KotlinSerializationConventionPlugin: Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        with(pluginManager) {
            apply("org.jetbrains.kotlin.plugin.serialization")
        }

        dependencies {
            "implementation"(libs.findLibrary("kotlin.serialization").get())
        }
    }
}