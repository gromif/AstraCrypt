package com.nevidimka655.domain.notes.model

interface Mapper<T, R> {

    operator fun invoke(item: T): R

}