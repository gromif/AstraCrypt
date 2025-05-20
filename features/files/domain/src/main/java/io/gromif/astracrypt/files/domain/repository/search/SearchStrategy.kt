package io.gromif.astracrypt.files.domain.repository.search

interface SearchStrategy<in T, out R> {

    suspend fun search(request: T): R

}
