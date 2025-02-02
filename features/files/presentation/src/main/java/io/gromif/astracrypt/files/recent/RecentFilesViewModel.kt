package io.gromif.astracrypt.files.recent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import dagger.hilt.android.lifecycle.HiltViewModel
import io.gromif.astracrypt.files.di.FilesImageLoader
import io.gromif.astracrypt.files.domain.usecase.GetRecentItemsUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class RecentFilesViewModel @Inject constructor(
    @FilesImageLoader
    val imageLoader: ImageLoader,
    getRecentItemsUseCase: GetRecentItemsUseCase,
): ViewModel() {
    val recentFilesStateFlow = getRecentItemsUseCase().stateIn(
        viewModelScope, SharingStarted.Eagerly, emptyList()
    )

}