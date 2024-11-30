package com.nevidimka655.astracrypt.tabs.settings.security.quick_actions

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.pm.PackageManager
import com.nevidimka655.astracrypt.tabs.settings.security.quick_actions.tiles.QuickDataDeletion
import com.nevidimka655.astracrypt.utils.Api
import com.nevidimka655.astracrypt.utils.Engine

object QuickActionsManager {
    private val context get() = Engine.appContext

    fun isComponentEnabled(componentName: ComponentName) = context.packageManager
        .getComponentEnabledSetting(componentName) == PackageManager.COMPONENT_ENABLED_STATE_ENABLED

    fun setComponentState(componentName: ComponentName, state: Boolean) = context.packageManager
        .setComponentEnabledSetting(
            componentName,
            if (state) {
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED
            } else PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        )

    fun setStateToAllComponents(state: Boolean) {
        listOf(
            Components.QUICK_DATA_DELETION
        ).forEach {
            setComponentState(
                componentName = it,
                state = state
            )
        }
    }

    fun isSupported() = Api.atLeastAndroid7()

    @SuppressLint("NewApi")
    object Components {

        val QUICK_DATA_DELETION get() = ComponentName(context, QuickDataDeletion::class.java)

    }

}