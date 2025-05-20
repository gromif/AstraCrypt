package io.gromif.astracrypt.files.data.repository.dataSource

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import io.gromif.astracrypt.files.data.db.FilesDao
import io.gromif.astracrypt.files.data.util.AeadHandler
import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.model.Item
import io.gromif.astracrypt.files.domain.model.ItemType
import io.gromif.astracrypt.files.domain.repository.DataSource
import kotlinx.coroutines.flow.Flow

class StarredDataSource(
    private val filesDao: FilesDao,
    private val pagingConfig: PagingConfig,
    private val aeadHandler: AeadHandler
) : DataSource<PagingData<Item>> {

    override suspend fun getDataFlow(
        folderId: Long,
        searchRequest: String?,
        aeadInfo: AeadInfo
    ): Flow<PagingData<Item>> {
        val searchQuery = searchRequest?.takeIf { it.isNotEmpty() }
        val sortSecondType = if (aeadInfo.name) ItemType.Folder.ordinal else ItemType.Other.ordinal
        return DefaultPagerFactory(
            pagingConfig = pagingConfig,
            aeadHandler = aeadHandler,
            aeadInfo = aeadInfo
        ) {
            filesDao.listStarred(
                query = searchQuery,
                sortingItemType = ItemType.Folder.ordinal,
                sortingSecondType = sortSecondType
            )
        }
    }

}