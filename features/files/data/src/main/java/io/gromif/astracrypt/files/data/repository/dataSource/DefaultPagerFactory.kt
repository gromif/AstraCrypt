package io.gromif.astracrypt.files.data.repository.dataSource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.map
import io.gromif.astracrypt.files.data.db.tuples.PagerTuple
import io.gromif.astracrypt.files.data.util.aead.AbstractAeadHandler
import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.model.AeadMode
import io.gromif.astracrypt.files.domain.model.FileSource
import io.gromif.astracrypt.files.domain.model.Item
import io.gromif.astracrypt.files.domain.model.ItemState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal object DefaultPagerFactory {

    operator fun invoke(
        pagingConfig: PagingConfig,
        pagerTupleAeadHandler: AbstractAeadHandler<PagerTuple>,
        aeadInfo: AeadInfo,
        pagingSourceFactory: () -> PagingSource<Int, PagerTuple>
    ): Flow<PagingData<Item>> = Pager(
        config = pagingConfig,
        pagingSourceFactory = pagingSourceFactory
    ).flow.map { pd ->
        pd.map { pagerTuple ->
            val databaseMode = aeadInfo.databaseMode
            val data = if (databaseMode is AeadMode.Template) {
                pagerTupleAeadHandler.decrypt(aeadInfo, databaseMode, pagerTuple)
            } else {
                pagerTuple
            }
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
