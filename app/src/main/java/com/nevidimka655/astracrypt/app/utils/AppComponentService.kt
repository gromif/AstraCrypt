package com.nevidimka655.astracrypt.app.utils

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import com.nevidimka655.astracrypt.app.services.tiles.WipeTile
import com.nevidimka655.astracrypt.view.MainActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

private const val ENABLED = PackageManager.COMPONENT_ENABLED_STATE_ENABLED
private const val DISABLED = PackageManager.COMPONENT_ENABLED_STATE_DISABLED

private typealias Component = ComponentName

@SuppressLint("NewApi")
class AppComponentService @Inject constructor(
    @ApplicationContext context: Context
) {
    private val packageManager = context.packageManager


    private val _main = Component(context, MainActivity::class.java)
    var main
        get() = getState(comp = _main) == ENABLED
        set(value) = setState(comp = _main, state = value)


    private val _calculator = Component(context, "${context.packageName}.MainActivityCalculator")
    var calculator
        get() = getState(comp = _calculator) == ENABLED
        set(value) = setState(comp = _calculator, state = value)


    private val _wipeTile = Component(context, WipeTile::class.java)
    var quickDataDeletion
        get() = getState(comp = _wipeTile) == ENABLED
        set(value) = setState(comp = _wipeTile, state = value)


    private fun getState(comp: Component) = packageManager.getComponentEnabledSetting(comp)

    private fun setState(
        comp: Component, state: Boolean
    ) = packageManager.setComponentEnabledSetting(
        comp, if (state) ENABLED else DISABLED, PackageManager.DONT_KILL_APP
    )

    companion object {
        val isActionsSupported = Api.atLeast7()
    }

}