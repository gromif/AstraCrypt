package io.gromif.astracrypt.files.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.paging.PagingData
import io.gromif.astracrypt.files.domain.model.FileItem
import io.gromif.astracrypt.files.domain.model.ViewMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class StateHolder(
    val isStarred: Boolean = false,
    val isSearching: Boolean = false,
    val viewMode: ViewMode = ViewMode.Grid,
    val pagingFlow: Flow<PagingData<FileItem>> = emptyFlow(),
    val multiselectStateList: SnapshotStateList<Long> = mutableStateListOf(),
    val backStackList: List<RootInfo> = listOf(
        RootInfo(name = "Root1"), RootInfo(name = "Root2"), RootInfo(name = "Root3")
    )
)