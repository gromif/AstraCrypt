package io.gromif.astracrypt.files.recent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.ImageLoader
import dagger.hilt.android.lifecycle.HiltViewModel
import io.gromif.astracrypt.files.di.coil.FilesImageLoader
import io.gromif.astracrypt.files.domain.usecase.GetRecentItemsUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class RecentFilesViewModel @Inject constructor(
    @FilesImageLoader
    val imageLoader: ImageLoader,
    getRecentItemsUseCase: GetRecentItemsUseCase,
) : ViewModel() {
    val recentItemsListState = getRecentItemsUseCase()
        .stateIn(viewModelScope, SharingStarted.Companion.WhileSubscribed(), listOf())
}