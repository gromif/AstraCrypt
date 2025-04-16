
import com.android.build.gradle.LibraryExtension
import io.gromif.buildlogic.AppConfig
import io.gromif.buildlogic.Plugins
import io.gromif.buildlogic.configureDefaultConfig
import io.gromif.buildlogic.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryConventionPlugin: Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        with(pluginManager) {
            apply(Plugins.ANDROID_LIBRARY)
            apply(Plugins.KOTLIN_ANDROID)
            apply(Plugins.DETEKT)
        }

        extensions.configure<LibraryExtension> {
            configureDefaultConfig(this)
            defaultConfig.targetSdk = AppConfig.SDK.TARGET
            configureKotlinAndroid()

            buildTypes {
                release {
                    isMinifyEnabled = false
                }
            }
        }
    }
}