package io.gromif.astracrypt.files.data.repository.search

import io.gromif.astracrypt.files.domain.repository.Repository
import io.gromif.astracrypt.files.domain.repository.search.SearchStrategy

class DefaultSearchStrategy(
    private val repository: Repository
): SearchStrategy<Long, List<Long>> {

    override suspend fun search(request: Long): List<Long> {
        return repository.getFolderIds(request)
    }

}
