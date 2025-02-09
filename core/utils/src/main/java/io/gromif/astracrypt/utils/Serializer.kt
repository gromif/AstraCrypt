package io.gromif.astracrypt.utils

fun interface Serializer<T, R> {

    operator fun invoke(item: T): R

}