import com.android.build.api.dsl.ApplicationExtension
import dagger.hilt.android.plugin.HiltExtension
import io.gromif.buildlogic.AppConfig
import io.gromif.buildlogic.Plugins
import io.gromif.buildlogic.configureDefaultConfig
import io.gromif.buildlogic.configureFlavors
import io.gromif.buildlogic.configureKotlinAndroid
import io.gromif.buildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

@Suppress("KotlinConstantConditions")
class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        with(pluginManager) {
            apply(Plugins.ANDROID_APP)
            apply(Plugins.HILT)
            apply(Plugins.KOTLIN_ANDROID)
            apply(Plugins.DETEKT)
        }

        extensions.configure<ApplicationExtension> {
            configureDefaultConfig(this)
            buildToolsVersion = AppConfig.Versions.BUILD_TOOLS
            with(defaultConfig) {
                applicationId = namespace
                targetSdk = AppConfig.SDK.TARGET
                versionCode = AppConfig.Versions.CODE
                versionName = AppConfig.Versions.NAME

                buildFeatures.buildConfig = true
            }
            configureKotlinAndroid()
            configureFlavors()
        }
        extensions.configure<HiltExtension> {
            enableAggregatingTask = true
        }

        dependencies {
            if (AppConfig.Tools.LEAK_CANARY) add(
                configurationName = "debugImplementation",
                dependencyNotation = libs.findLibrary("leakcanary").get()
            )
        }
    }
}