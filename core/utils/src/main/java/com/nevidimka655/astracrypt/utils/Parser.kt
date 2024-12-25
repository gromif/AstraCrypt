package com.nevidimka655.astracrypt.utils

interface Parser<T, R> {

    operator fun invoke(item: T): R

}