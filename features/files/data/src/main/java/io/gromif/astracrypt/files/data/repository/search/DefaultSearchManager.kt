package io.gromif.astracrypt.files.data.repository.search

import io.gromif.astracrypt.files.domain.repository.search.SearchManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class DefaultSearchManager : SearchManager {
    private val searchQueryState = MutableStateFlow<String?>(null)

    override fun setSearchRequest(query: String?) {
        searchQueryState.update { query }
    }

    override fun getSearchRequestFlow(): Flow<String?> {
        return searchQueryState
    }
}
