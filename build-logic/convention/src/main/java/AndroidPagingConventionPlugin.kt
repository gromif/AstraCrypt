import com.nevidimka655.buildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidPagingConventionPlugin: Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        dependencies {
            add("implementation", libs.findLibrary("paging.compose").get())
            add("implementation", libs.findLibrary("paging.runtime.ktx").get())
        }
    }
}