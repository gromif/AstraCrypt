package com.nevidimka655.astracrypt.utils.extensions

import android.view.WindowManager
import androidx.appcompat.app.AlertDialog

fun AlertDialog.openKeyboard() = this.apply {
    window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
}