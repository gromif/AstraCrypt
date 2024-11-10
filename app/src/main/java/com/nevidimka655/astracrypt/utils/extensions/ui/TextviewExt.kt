package com.nevidimka655.astracrypt.utils.extensions.ui

import android.widget.TextView
import androidx.annotation.DrawableRes

fun TextView.setDrawableTop(@DrawableRes resId: Int) =
    this.setCompoundDrawablesRelativeWithIntrinsicBounds(
        0, resId, 0, 0
    )