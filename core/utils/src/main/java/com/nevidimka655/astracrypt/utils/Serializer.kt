package com.nevidimka655.astracrypt.utils

interface Serializer<T, R> {

    operator fun invoke(item: T): R

}