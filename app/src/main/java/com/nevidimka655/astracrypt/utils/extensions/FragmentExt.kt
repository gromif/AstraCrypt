package com.nevidimka655.astracrypt.utils.extensions

import androidx.core.view.MenuHost
import androidx.fragment.app.Fragment

fun Fragment.requireMenuHost() = requireActivity() as MenuHost