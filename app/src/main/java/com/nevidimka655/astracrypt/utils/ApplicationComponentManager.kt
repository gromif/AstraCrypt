package com.nevidimka655.astracrypt.utils

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import com.nevidimka655.astracrypt.MainActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ApplicationComponentManager @Inject constructor(
    @ApplicationContext context: Context
) {
    private val packageManager = context.packageManager
    private val main = ComponentName(context, MainActivity::class.java)
    private val calculator = ComponentName(context, "${context.packageName}.MainActivityCalculator")

    private fun getComponentState(state: Boolean) = if (state) {
        PackageManager.COMPONENT_ENABLED_STATE_ENABLED
    } else PackageManager.COMPONENT_ENABLED_STATE_DISABLED

    fun setMainActivityState(state: Boolean) = packageManager.setComponentEnabledSetting(
        main, getComponentState(state = state), PackageManager.DONT_KILL_APP
    )

    fun setCalculatorActivityState(state: Boolean) = packageManager.setComponentEnabledSetting(
        calculator, getComponentState(state = state), PackageManager.DONT_KILL_APP
    )

}