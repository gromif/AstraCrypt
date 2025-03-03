package io.gromif.astracrypt.files.recent

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.gromif.astracrypt.files.recent.list.Actions
import io.gromif.astracrypt.files.recent.list.RecentFilesList

@Composable
fun RecentFilesComponent(
    actions: Actions
) {
    val vm: RecentFilesViewModel = hiltViewModel()

    val list by vm.recentItemsListState.collectAsStateWithLifecycle()
    if (list.isNotEmpty()) RecentFilesList(
        list = list,
        imageLoader = vm.imageLoader,
        actions = actions
    )
}