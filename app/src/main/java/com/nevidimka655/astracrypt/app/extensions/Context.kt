package com.nevidimka655.astracrypt.app.extensions

import android.app.admin.DevicePolicyManager
import android.content.ClipboardManager
import android.content.Context

fun Context.clipboardManager() =
    getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager