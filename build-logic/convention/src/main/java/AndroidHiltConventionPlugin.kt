import io.gromif.buildlogic.Plugins
import io.gromif.buildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.exclude

class AndroidHiltConventionPlugin: Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        with(pluginManager) {
            apply(Plugins.HILT)
            apply(Plugins.KSP)
        }

        configurations.all {
            exclude(group = "androidx.viewpager")
        }

        dependencies {
            "implementation"(libs.findLibrary("hilt.android").get())
            "implementation"(libs.findLibrary("hilt.work").get())
            "ksp"(libs.findLibrary("hilt.compiler").get())
            "ksp"(libs.findLibrary("hilt.android.compiler").get())

            "kspAndroidTest"(libs.findLibrary("hilt.compiler").get())
            "kspTest"(libs.findLibrary("hilt.compiler").get())
            "kspAndroidTest"(libs.findLibrary("hilt.android.compiler").get())
            "kspTest"(libs.findLibrary("hilt.android.compiler").get())
        }
    }
}