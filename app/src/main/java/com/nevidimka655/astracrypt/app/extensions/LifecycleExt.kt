package com.nevidimka655.astracrypt.app.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope

val Fragment.viewLifecycleScope get() = viewLifecycleOwner.lifecycleScope