package io.gromif.astracrypt.utils.app

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager

private const val ENABLED = PackageManager.COMPONENT_ENABLED_STATE_ENABLED
private const val DISABLED = PackageManager.COMPONENT_ENABLED_STATE_DISABLED

private typealias Component = ComponentName

@SuppressLint("NewApi")
class AppComponentService(context: Context) {
    private val packageManager = context.packageManager


    private val _main = Component(context, "${context.packageName}.view.MainActivity")
    var main
        get() = getState(comp = _main) == ENABLED
        set(value) = setState(comp = _main, state = value)


    private val _calculator = Component(context, "${context.packageName}.MainActivityCalculator")
    var calculator
        get() = getState(comp = _calculator) == ENABLED
        set(value) = setState(comp = _calculator, state = value)


    private fun getState(comp: Component) = packageManager.getComponentEnabledSetting(comp)

    private fun setState(
        comp: Component, state: Boolean
    ) = packageManager.setComponentEnabledSetting(
        comp, if (state) ENABLED else DISABLED, PackageManager.DONT_KILL_APP
    )

}