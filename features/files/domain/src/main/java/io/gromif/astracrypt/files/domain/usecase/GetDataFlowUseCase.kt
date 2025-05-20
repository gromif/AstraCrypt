package io.gromif.astracrypt.files.domain.usecase

import kotlinx.coroutines.flow.flatMapLatest

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class GetDataFlowUseCase<T>(
    private val getCurrentNavFolderFlowUseCase: io.gromif.astracrypt.files.domain.usecase.navigator.GetCurrentNavFolderFlowUseCase,
    private val getSearchRequestFlow: io.gromif.astracrypt.files.domain.usecase.search.GetSearchRequestFlow,
    private val dataSource: io.gromif.astracrypt.files.domain.repository.DataSource<T>
) {

    operator fun invoke(): kotlinx.coroutines.flow.Flow<T> {
        val currentNavFolderFlow = getCurrentNavFolderFlowUseCase()
        val searchRequestFlow = getSearchRequestFlow()

        return kotlinx.coroutines.flow.combine(
            currentNavFolderFlow,
            searchRequestFlow
        ) { it }.flatMapLatest { resultArray ->
            val currentNavFolder = resultArray[0] as io.gromif.astracrypt.files.domain.repository.StorageNavigator.Folder
            val searchRequest = resultArray[1] as String?

            dataSource.getDataFlow(
                folderId = currentNavFolder.id,
                searchRequest = searchRequest
            )
        }
    }

}