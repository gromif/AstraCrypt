package com.nevidimka655.astracrypt.utils.extensions.ui

import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.widget.TooltipCompat

fun View.setTooltip(@StringRes tooltipTextResId: Int) = TooltipCompat.setTooltipText(
    this,
    this.context.getText(tooltipTextResId)
)