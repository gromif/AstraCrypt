package io.gromif.astracrypt.files.recent

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import dagger.hilt.android.lifecycle.HiltViewModel
import io.gromif.astracrypt.files.di.FilesImageLoader
import io.gromif.astracrypt.files.domain.model.Item
import io.gromif.astracrypt.files.domain.usecase.GetRecentItemsUseCase
import io.gromif.astracrypt.utils.dispatchers.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class RecentFilesViewModel @Inject constructor(
    @IoDispatcher
    private val defaultDispatcher: CoroutineDispatcher,
    @FilesImageLoader
    val imageLoader: ImageLoader,
    getRecentItemsUseCase: GetRecentItemsUseCase,
): ViewModel() {
    val recentFilesStateList = mutableStateListOf<Item>()

    init {
        viewModelScope.launch(defaultDispatcher) {
            getRecentItemsUseCase().collectLatest {
                recentFilesStateList.clear()
                recentFilesStateList.addAll(it)
            }
        }
    }

}