package com.nevidimka655.astracrypt.utils.extensions.ui

import androidx.fragment.app.Fragment
import com.nevidimka655.astracrypt.MainActivity

fun Fragment.requireMainActivity() = this.requireActivity() as MainActivity