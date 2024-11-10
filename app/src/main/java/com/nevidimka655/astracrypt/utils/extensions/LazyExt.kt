package com.nevidimka655.astracrypt.utils.extensions

fun <T> lazyFast(initializer: () -> T): Lazy<T> =
    lazy(LazyThreadSafetyMode.NONE, initializer)