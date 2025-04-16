import io.gromif.buildlogic.Plugins
import io.gromif.buildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidRoomConventionPlugin: Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        pluginManager.apply(Plugins.KSP)

        dependencies {
            add("implementation", libs.findLibrary("room.runtime").get())
            add("implementation", libs.findLibrary("room.ktx").get())
            add("implementation", libs.findLibrary("room.paging").get())
            add("ksp", libs.findLibrary("room.compiler").get())
        }
    }
}