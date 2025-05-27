package io.gromif.astracrypt.files.domain.usecase.search

import io.gromif.astracrypt.files.domain.repository.search.SearchManager

class RequestSearchUseCase(
    private val searchManager: SearchManager
) {

    operator fun invoke(request: String?) {
        searchManager.setSearchRequest(request)
    }
}
