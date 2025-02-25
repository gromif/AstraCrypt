import com.android.build.api.dsl.ApplicationExtension
import dagger.hilt.android.plugin.HiltExtension
import io.gromif.buildlogic.AppConfig
import io.gromif.buildlogic.configureDefaultConfig
import io.gromif.buildlogic.configureFlavors
import io.gromif.buildlogic.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        with(pluginManager) {
            apply("com.android.application")
            apply("dagger.hilt.android.plugin")
            apply("org.jetbrains.kotlin.android")
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
    }
}