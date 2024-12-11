package com.nevidimka655.astracrypt.view.ui.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope

val Fragment.viewLifecycleScope get() = viewLifecycleOwner.lifecycleScope