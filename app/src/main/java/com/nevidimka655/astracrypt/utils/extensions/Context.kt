package com.nevidimka655.astracrypt.utils.extensions

import android.app.admin.DevicePolicyManager
import android.content.ClipboardManager
import android.content.Context

fun Context.devicePolicyManager() =
    getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager

fun Context.clipboardManager() =
    getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager