package io.gromif.astracrypt.utils

fun interface Parser<T, R> {

    operator fun invoke(item: T): R

}