package io.gromif.astracrypt.files.data.repository.dataSource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import io.gromif.astracrypt.files.data.db.FilesDao
import io.gromif.astracrypt.files.data.util.AeadHandler
import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.model.AeadMode
import io.gromif.astracrypt.files.domain.model.FileSource
import io.gromif.astracrypt.files.domain.model.Item
import io.gromif.astracrypt.files.domain.model.ItemState
import io.gromif.astracrypt.files.domain.model.ItemType
import io.gromif.astracrypt.files.domain.repository.DataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

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
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                filesDao.listStarred(
                    query = searchQuery,
                    sortingItemType = ItemType.Folder.ordinal,
                    sortingSecondType = sortSecondType
                )
            }
        ).flow.map { pd ->
            pd.map { pagerTuple ->
                val databaseMode = aeadInfo.databaseMode
                val data = if (databaseMode is AeadMode.Template) {
                    aeadHandler.decryptPagerTuple(aeadInfo, databaseMode, pagerTuple)
                } else pagerTuple
                Item(
                    id = data.id,
                    name = data.name,
                    type = data.type,
                    preview = data.preview?.let {
                        FileSource(path = it, aeadIndex = data.previewAead)
                    },
                    state = ItemState.entries[data.state]
                )
            }
        }
    }

}