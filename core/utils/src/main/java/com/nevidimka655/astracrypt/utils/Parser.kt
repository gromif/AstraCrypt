package com.nevidimka655.astracrypt.utils

fun interface Parser<T, R> {

    operator fun invoke(item: T): R

}