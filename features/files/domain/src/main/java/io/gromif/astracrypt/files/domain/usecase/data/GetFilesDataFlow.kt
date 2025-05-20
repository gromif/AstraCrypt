package io.gromif.astracrypt.files.domain.usecase.data

import io.gromif.astracrypt.files.domain.repository.DataSource
import io.gromif.astracrypt.files.domain.usecase.search.GetSearchRequestFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest

@OptIn(ExperimentalCoroutinesApi::class)
class GetFilesDataFlow<T>(
    private val getSearchRequestFlow: GetSearchRequestFlow,
    private val dataSource: DataSource<T>
) {

    operator fun invoke(): Flow<T> {
        val searchRequestFlow = getSearchRequestFlow()

        return combine(
            searchRequestFlow
        ) { it }.flatMapLatest { resultArray ->
            val searchRequest = resultArray[0]

            dataSource.provideFiles(
                searchRequest = searchRequest
            )
        }
    }

}
