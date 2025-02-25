@file:Suppress("ClassName", "SpellCheckingInspection")

package io.gromif.buildlogic.flavor

import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import io.gromif.buildlogic.Flavor
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

private const val DIMENSION_MARKET = "market"
private const val FLAVOR_FDROID = "fdroid"
private const val FLAVOR_PLAY = "play"

class FlavorMarketConventionPlugin: Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        extensions.configure<LibraryExtension> {
            Flavor.createMarket(this)
        }
    }
}

internal fun Flavor.createMarket(
    commonExtension: CommonExtension<*,*,*,*,*,*>
): Unit = with(commonExtension) {
    flavorDimensions += DIMENSION_MARKET
    productFlavors {
        register(FLAVOR_FDROID) {
            dimension = DIMENSION_MARKET
        }
        register(FLAVOR_PLAY) {
            dimension = DIMENSION_MARKET
        }
    }
}