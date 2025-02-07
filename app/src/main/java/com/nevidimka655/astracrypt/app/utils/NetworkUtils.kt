package com.nevidimka655.astracrypt.app.utils

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri

object NetworkUtils {
    fun openUri(context: Context, url: String) =
        context.startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))

    fun openAppGooglePlayPage(context: Context) = openUri(
        context = context,
        url = "https://play.google.com/store/apps/details?id=com.nevidimka655.astracrypt"
    )
}