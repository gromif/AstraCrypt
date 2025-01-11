import org.gradle.api.Plugin
import org.gradle.api.Project

class KotlinParcelizeConventionPlugin: Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        with(pluginManager) {
            apply("org.jetbrains.kotlin.plugin.parcelize")
        }
    }
}