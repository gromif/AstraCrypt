import com.android.build.gradle.LibraryExtension
import com.nevidimka655.buildlogic.AppConfig
import com.nevidimka655.buildlogic.configureDefaultConfig
import com.nevidimka655.buildlogic.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryConventionPlugin: Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        with(pluginManager) {
            apply("com.android.library")
            apply("org.jetbrains.kotlin.android")
        }

        extensions.configure<LibraryExtension> {
            configureDefaultConfig(this)
            defaultConfig.targetSdk = AppConfig.TARGET_SDK
            configureKotlinAndroid()

            buildTypes {
                release {
                    isMinifyEnabled = false
                }
            }
        }
    }
}