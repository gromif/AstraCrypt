package io.gromif.astracrypt.files.domain.repository.search

interface SearchStrategy<T, R> {

    suspend fun search(request: T): R

}
