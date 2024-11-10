package com.nevidimka655.astracrypt.utils

import android.content.ComponentName
import android.content.pm.PackageManager
import com.nevidimka655.astracrypt.MainActivity

object ApplicationComponentManager {

    fun setMainActivityState(state: Boolean) {
        val context = Engine.appContext
        context.packageManager.setComponentEnabledSetting(
            ComponentName(context, MainActivity::class.java),
            if (state) PackageManager.COMPONENT_ENABLED_STATE_ENABLED
            else PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        )
    }

    fun setCalculatorActivityState(state: Boolean) {
        val context = Engine.appContext
        context.packageManager.setComponentEnabledSetting(
            ComponentName(context, "${context.packageName}.MainActivityCalculator"),
            if (state) PackageManager.COMPONENT_ENABLED_STATE_ENABLED
            else PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        )
    }

}