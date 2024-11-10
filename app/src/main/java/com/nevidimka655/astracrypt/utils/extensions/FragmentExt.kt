package com.nevidimka655.astracrypt.utils.extensions

import androidx.annotation.IdRes
import androidx.core.view.MenuHost
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar

fun Fragment.requireToolbar(@IdRes id: Int) = requireActivity().findViewById<MaterialToolbar>(id)

fun Fragment.requireMenuHost() = requireActivity() as MenuHost