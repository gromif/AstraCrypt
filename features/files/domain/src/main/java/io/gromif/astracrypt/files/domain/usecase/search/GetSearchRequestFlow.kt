package io.gromif.astracrypt.files.domain.usecase.search

import io.gromif.astracrypt.files.domain.repository.search.SearchManager
import kotlinx.coroutines.flow.Flow

class GetSearchRequestFlow(
    private val searchManager: SearchManager
) {

    operator fun invoke(): Flow<String?> {
        return searchManager.getSearchRequestFlow()
    }
}
