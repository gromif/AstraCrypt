package com.nevidimka655.astracrypt.utils

fun interface Mapper<T : Any, R : Any> {

    operator fun invoke(item: T): R

}