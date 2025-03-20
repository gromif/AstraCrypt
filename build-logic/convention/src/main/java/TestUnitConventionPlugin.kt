import io.gromif.buildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class TestUnitConventionPlugin: Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        dependencies {
            "testImplementation"(libs.findLibrary("junit").get())
            "testImplementation"(libs.findLibrary("mockk").get())
        }
    }
}