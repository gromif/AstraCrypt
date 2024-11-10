package com.nevidimka655.astracrypt.utils.appearance

import androidx.core.content.edit
import com.nevidimka655.astracrypt.utils.shared_prefs.PrefsManager

object AppearanceManager {
    private val prefs get() = PrefsManager.clear

    private var cachedViewMode: ViewMode? = null
    val viewMode
        get() = cachedViewMode ?: prefs.getInt(Keys.VIEW_MODE, ViewMode.Grid.ordinal).run {
            val value = ViewMode.entries[this]
            cachedViewMode = value
            value
        }

    private var cachedUseDynamicColors: Boolean? = null
    val useDynamicColors
        get() = cachedUseDynamicColors ?: prefs.getBoolean(Keys.DYNAMIC_COLORS_STATE, true)
            .also { cachedUseDynamicColors = it }

    fun setViewMode(newViewMode: ViewMode) = prefs.edit {
        cachedViewMode = newViewMode
        putInt(Keys.VIEW_MODE, newViewMode.ordinal)
    }

    fun setUseDynamicColors(state: Boolean) = prefs.edit {
        cachedUseDynamicColors = state
        putBoolean(Keys.DYNAMIC_COLORS_STATE, state)
    }

    object Keys {
        const val VIEW_MODE = "zephyr"
        const val DYNAMIC_COLORS_STATE = "lime"
    }

}