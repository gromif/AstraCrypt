package io.gromif.astracrypt.files.domain.usecase

import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.repository.DataSource
import io.gromif.astracrypt.files.domain.repository.StorageNavigator
import io.gromif.astracrypt.files.domain.usecase.aead.GetAeadInfoFlowUseCase
import io.gromif.astracrypt.files.domain.usecase.navigator.GetCurrentNavFolderFlowUseCase
import io.gromif.astracrypt.files.domain.usecase.search.GetSearchRequestFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest

@OptIn(ExperimentalCoroutinesApi::class)
class GetDataFlowUseCase<T>(
    private val getCurrentNavFolderFlowUseCase: GetCurrentNavFolderFlowUseCase,
    private val getAeadInfoFlowUseCase: GetAeadInfoFlowUseCase,
    private val getSearchRequestFlow: GetSearchRequestFlow,
    private val dataSource: DataSource<T>
) {

    operator fun invoke(): Flow<T> {
        val currentNavFolderFlow = getCurrentNavFolderFlowUseCase()
        val aeadInfoFlow = getAeadInfoFlowUseCase()
        val searchRequestFlow = getSearchRequestFlow()

        return combine(
            currentNavFolderFlow,
            aeadInfoFlow,
            searchRequestFlow
        ) { it }.flatMapLatest { resultArray ->
            val currentNavFolder = resultArray[0] as StorageNavigator.Folder
            val aeadInfo = resultArray[1] as AeadInfo
            val searchRequest = resultArray[2] as String?

            dataSource.getDataFlow(
                folderId = currentNavFolder.id,
                searchRequest = searchRequest,
                aeadInfo = aeadInfo
            )
        }
    }

}