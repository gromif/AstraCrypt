import io.gromif.buildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidCoilConventionPlugin: Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        dependencies {
            add("implementation", libs.findLibrary("coil.compose").get())
            add("implementation", libs.findLibrary("coil.video").get())
        }
    }
}