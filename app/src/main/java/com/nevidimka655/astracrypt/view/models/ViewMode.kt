package com.nevidimka655.astracrypt.view.models

import androidx.annotation.StringRes
import com.nevidimka655.astracrypt.resources.R

enum class ViewMode(@StringRes val stringResId: Int) {
    Grid(stringResId = R.string.viewMode_grid),
    ListDefault(stringResId = R.string.viewMode_list)
}