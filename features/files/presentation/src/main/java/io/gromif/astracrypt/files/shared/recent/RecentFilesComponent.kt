package io.gromif.astracrypt.files.shared.recent

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.gromif.astracrypt.files.shared.recent.list.Actions
import io.gromif.astracrypt.files.shared.recent.list.RecentFilesList

@Composable
fun RecentFilesComponent(
    actions: Actions
) {
    val vm: RecentFilesViewModel = hiltViewModel()

    val list by vm.recentFilesStateFlow.collectAsStateWithLifecycle()
    if (list.isNotEmpty()) RecentFilesList(
        list = list,
        imageLoader = vm.imageLoader,
        actions = actions
    )
}