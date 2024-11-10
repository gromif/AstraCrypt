package com.nevidimka655.astracrypt.utils

import android.content.Context
import com.google.android.material.color.MaterialColors
import com.google.android.material.elevation.SurfaceColors

object ColorManager {
    var colorSurface = 0
    var navigationBarColor = 0
    var colorSecondary = 0

    fun initialize(context: Context) {
        colorSurface =
            MaterialColors.getColor(context, com.google.android.material.R.attr.colorSurface, 0)
        navigationBarColor = SurfaceColors.SURFACE_2.getColor(context)
        colorSecondary =
            MaterialColors.getColor(context, com.google.android.material.R.attr.colorSecondary, 0)
    }

    fun reset() {
        colorSurface = 0
        navigationBarColor = 0
        colorSecondary = 0
    }

}