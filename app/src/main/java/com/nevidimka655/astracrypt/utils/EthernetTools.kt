package com.nevidimka655.astracrypt.utils

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri

object EthernetTools {
    fun openUri(context: Context, url: String) =
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))

    fun openAppGooglePlayPage(context: Context) = openUri(
        context = context,
        url = "https://play.google.com/store/apps/details?id=com.nevidimka655.astracrypt"
    )

    fun isConnected(context: Context = Engine.appContext): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetwork != null && cm.getNetworkCapabilities(cm.activeNetwork) != null
    }
}