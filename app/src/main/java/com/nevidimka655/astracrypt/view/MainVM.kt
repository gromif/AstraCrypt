package com.nevidimka655.astracrypt.view

import android.net.Uri
import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.app.utils.Io
import com.nevidimka655.astracrypt.app.utils.SelectorManager
import com.nevidimka655.astracrypt.app.utils.SetupManager
import com.nevidimka655.astracrypt.app.utils.ToolsManager
import com.nevidimka655.astracrypt.data.datastore.AppearanceManager
import com.nevidimka655.astracrypt.data.model.NavigatorDirectory
import com.nevidimka655.astracrypt.data.paging.FilesPagingProvider
import com.nevidimka655.astracrypt.data.paging.StarredPagingProvider
import com.nevidimka655.astracrypt.data.repository.files.FilesRepositoryProvider
import com.nevidimka655.astracrypt.domain.room.StorageItemMinimalTuple
import com.nevidimka655.astracrypt.view.models.UiState
import com.nevidimka655.astracrypt.view.models.ViewMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(
    private val filesRepositoryProvider: FilesRepositoryProvider,
    private val setupManager: SetupManager,
    private val io: Io,
    private val filesPagingProvider: FilesPagingProvider,
    private val starredPagingProvider: StarredPagingProvider,
    val appearanceManager: AppearanceManager
) : ViewModel() {
    var uiState = mutableStateOf(UiState())
    val filesViewModeState = appearanceManager.filesViewModeFlow.stateIn(
        viewModelScope, SharingStarted.Lazily, initialValue = ViewMode.Grid
    )
    val selectorManager by lazy { SelectorManager() }

    var isSearching by mutableStateOf(false)
    val searchQuery = mutableStateOf<String?>(null)
    private var searchSetupJob: Job? = null
    private var searchDirsIndexesList = mutableStateListOf<Long>()

    val filesNavigatorList = mutableStateListOf<NavigatorDirectory>()
    val currentFolderId get() = filesNavigatorList.lastOrNull()?.id ?: 0

    var lastUriListToImport: List<Uri>? = null

    private var _searchChannel: Channel<String>? = null

    val toolsManager = ToolsManager()

    var isStarredScreen = false

    val pagingFlow = filesPagingProvider.createPagingSource(
        filesNavigatorList = filesNavigatorList,
        searchQuery = searchQuery,
        searchDirsIndexesList = searchDirsIndexesList
    ).cachedIn(viewModelScope)

    val starredPagingFlow = starredPagingProvider.createPagingSource(
        lastSearchQuery = searchQuery.value
    ).cachedIn(viewModelScope)

    /*val notesPagingFlow = Pager(
        PagingConfig(
            pageSize = 10,
            enablePlaceholders = false,
            initialLoadSize = 10
        ),
        pagingSourceFactory = { repository.getNotesList() }
    ).flow.map { pagingData ->
        val timePattern = "d MMMM yyyy"
        repositoryEncryption.decryptNotesPager(pagingData).map {
            Notes.Item(
                id = it.id,
                title = it.name,
                textPreview = it.textPreview?.run { "$this..." },
                creationTime = DateFormat.format(timePattern, it.creationTime).toString()
            )
        }
    }.cachedIn(viewModelScope)*/

    fun openDirectory(
        id: Long,
        dirName: String,
        popBackStack: Boolean = false
    ) = viewModelScope.launch {
        val formattedFolderName = if (dirName.length > 20) "${dirName.take(20)}.." else dirName
        val navigatorDirectory = NavigatorDirectory(id, formattedFolderName)
        with(filesNavigatorList) {
            if (popBackStack) {
                clear()
            }
            add(navigatorDirectory)
        }
        triggerListUpdate()
    }

    fun openDirectoryFromSelector(index: Int?) = viewModelScope.launch {
        if (index == null) {
            filesNavigatorList.clear()
            triggerListUpdate()
            return@launch
        } else {
            val selectedNavigatorDir = filesNavigatorList[index]
            if (currentFolderId != selectedNavigatorDir.id) {
                filesNavigatorList.removeRange(index + 1, filesNavigatorList.size)
                triggerListUpdate()
            }
        }
    }

    fun closeDirectory() {
        filesNavigatorList.removeAt(filesNavigatorList.lastIndex)
        triggerListUpdate()
    }

    private fun triggerListUpdate() {
        if (isStarredScreen) triggerStarredListUpdate() else triggerFilesListUpdate()
    }

    private fun triggerStarredListUpdate() = starredPagingProvider.invalidate()

    fun triggerFilesListUpdate() = filesPagingProvider.invalidate()

    fun newDirectory(directoryName: String) = viewModelScope.launch(Dispatchers.IO) {
        filesRepositoryProvider.filesRepository.first().newDirectory(
            name = directoryName,
            parentId = filesNavigatorList.lastOrNull()?.id
        )
        showSnackbar(R.string.snack_folderCreated)
    }

    fun delete(storageItemId: Long) = viewModelScope.launch(Dispatchers.IO) {
        val repository = filesRepositoryProvider.filesRepository.first()
        val idsList = arrayListOf<Long>()
        val itemToDelete = repository.getMinimalItemData(storageItemId)
        suspend fun deleteIterator(itemToDelete: StorageItemMinimalTuple) {
            idsList.add(itemToDelete.id)
            if (itemToDelete.path.isNotEmpty()) {
                val localFile = io.getLocalFile(itemToDelete.path)
                if (localFile.exists()) {
                    val thumbLocalFile = io.getLocalFile(itemToDelete.preview)
                    thumbLocalFile.delete()
                    localFile.delete()
                }
            } else repository.getMinimalItemsDataInDir(itemToDelete.id).forEach {
                deleteIterator(it)
            }
        }
        deleteIterator(itemToDelete)
        repository.deleteByIds(idsList)
        showSnackbar(
            if (itemToDelete.path.isEmpty()) R.string.snack_folderDeleted
            else R.string.snack_itemsDeleted
        )
    }

    fun deleteSelected(itemsArr: List<Long>) = viewModelScope.launch(Dispatchers.IO) {
        itemsArr.forEach { delete(it) }
        showSnackbar(R.string.snack_itemsDeleted)
    }

    fun move(itemsArr: List<Long>, movingDirId: Long?) = viewModelScope.launch(Dispatchers.IO) {
        val repository = filesRepositoryProvider.filesRepository.first()
        repository.moveItems(
            idsArray = itemsArr,
            newDirId = movingDirId ?: 0
        )
        showSnackbar(R.string.snack_itemsMoved)
    }

    @OptIn(FlowPreview::class)
    fun setSearchIsEnabled(state: Boolean) {
        if (state) {
            if (searchSetupJob != null) return
            searchSetupJob = viewModelScope.launch {
                val idToIterate = currentFolderId
                if (idToIterate.toInt() != 0) withContext(Dispatchers.IO) {
                    searchDirsIndexesList.clear()
                    val array = searchDirsIndexesList
                    val repository = filesRepositoryProvider.filesRepository.first()
                    suspend fun iterate(id: Long) {
                        array.add(id)
                        repository.getDirIdsList(id).forEach {
                            iterate(it)
                        }
                    }
                    iterate(idToIterate)
                }
                Channel<String>(0).also { _searchChannel = it }
                    .receiveAsFlow()
                    .debounce(200)
                    .collectLatest {
                        if (it.isNotEmpty()) {
                            searchQuery.value = it
                            triggerListUpdate()
                        }
                    }
            }
        } else {
            searchSetupJob?.cancel()
            searchDirsIndexesList.clear()
            searchSetupJob = null
            _searchChannel = null
            triggerListUpdate()
        }
    }

    fun searchQuerySubmit(query: String) {
        isSearching = true
        _searchChannel?.trySend(query)
    }

    private suspend fun showSnackbar(@StringRes stringId: Int) {}

    fun cacheUiState() {}

    fun updateCamouflageFeatureAccess(): Boolean {
        /*viewModelScope.launch(Dispatchers.IO) {
            if (authManager.info.camouflage !is Camouflage.None) {
                authManager.saveInfo(authManager.info.copy(camouflage = Camouflage.None))
                with(ApplicationComponentManager) {
                    setMainActivityState(true)
                    setCalculatorActivityState(false)
                }
            }
        }*/
        return true
    }

    init {
        if (!setupManager.isDatabaseCreated()) viewModelScope.launch { setupManager.setup() }
    }

}