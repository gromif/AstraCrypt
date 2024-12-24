package com.nevidimka655.astracrypt.utils

interface Mapper<T, R> {

    operator fun invoke(item: T): R

}