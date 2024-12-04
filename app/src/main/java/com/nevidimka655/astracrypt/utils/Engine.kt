package com.nevidimka655.astracrypt.utils

import android.content.Context

/**
 * Contains singleton dependencies.
 */
object Engine {
    lateinit var appContext: Context

    /**
     * Call this method in all application components that may be created at app startup/restoring
     * (e.g. in onCreate of activities and services)
     */
    fun init(context: Context) {
        if (!::appContext.isInitialized) {
            appContext = context
        }
    }

}