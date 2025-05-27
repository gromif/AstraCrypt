package io.gromif.astracrypt.files.data.repository.search

import io.gromif.astracrypt.files.domain.repository.item.ItemReader
import io.gromif.astracrypt.files.domain.repository.search.SearchStrategy

class DefaultSearchStrategy(
    private val itemReader: ItemReader
) : SearchStrategy<Long, List<Long>> {
    private var cachedStrategy: Pair<Long, List<Long>>? = null

    override suspend fun search(request: Long): List<Long> {
        val cachedRequestResult = cachedStrategy?.takeIf { it.first == request }?.second

        return cachedRequestResult ?: itemReader.getFolderIds(request).also {
            cachedStrategy = Pair(request, it)
        }
    }
}
