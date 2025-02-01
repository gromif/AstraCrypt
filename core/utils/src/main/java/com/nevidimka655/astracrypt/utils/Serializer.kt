package com.nevidimka655.astracrypt.utils

fun interface Serializer<T, R> {

    operator fun invoke(item: T): R

}