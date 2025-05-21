package io.gromif.astracrypt.files.domain.repository.search

import kotlinx.coroutines.flow.Flow

interface SearchManager {

    fun setSearchRequest(query: String?)

    fun getSearchRequestFlow(): Flow<String?>
}
