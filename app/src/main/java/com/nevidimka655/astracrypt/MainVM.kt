package com.nevidimka655.astracrypt

import android.app.Activity
import android.net.Uri
import android.text.format.DateFormat
import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import androidx.paging.map
import coil.ImageLoader
import com.nevidimka655.astracrypt.features.profile.ProfileInfo
import com.nevidimka655.astracrypt.model.NavigatorDirectory
import com.nevidimka655.astracrypt.room.Repository
import com.nevidimka655.astracrypt.room.RepositoryEncryption
import com.nevidimka655.astracrypt.room.StorageItemListTuple
import com.nevidimka655.astracrypt.room.StorageItemMinimalTuple
import com.nevidimka655.astracrypt.ui.UiStateOld
import com.nevidimka655.astracrypt.utils.AppConfig
import com.nevidimka655.astracrypt.utils.EncryptionManager
import com.nevidimka655.astracrypt.utils.Engine
import com.nevidimka655.astracrypt.utils.Io
import com.nevidimka655.astracrypt.utils.PrivacyPolicyManager
import com.nevidimka655.astracrypt.utils.SelectorManager
import com.nevidimka655.astracrypt.utils.ToolsManager
import com.nevidimka655.astracrypt.utils.datastore.AppearanceManager
import com.nevidimka655.crypto.tink.KeysetFactory
import com.nevidimka655.notes.Notes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(
    private val repository: Repository,
    private val repositoryEncryption: RepositoryEncryption,
    private val keysetFactory: KeysetFactory,
    private val io: Io,
    private val encryptionManager: EncryptionManager,
    val appearanceManager: AppearanceManager,
    val privacyPolicyManager: PrivacyPolicyManager,
    val imageLoader: ImageLoader
) : ViewModel() {
    val selectorManager by lazy { SelectorManager() }
    val encryptionInfo get() = encryptionManager.encryptionInfo

    var isSearchExpandedState by mutableStateOf(false)
    private var searchSetupJob: Job? = null
    private var searchDirsIndexesList: ArrayList<Long>? = null

    val filesNavigatorList = mutableStateListOf<NavigatorDirectory>()
    val currentNavigatorDirectoryId get() = filesNavigatorList.lastOrNull()?.id ?: 0

    var lastUriListToImport: List<Uri>? = null

    private var _searchChannel: Channel<String>? = null
    var lastSearchQuery: String? = null

    private val _snackbarChannel = Channel<Int>(0)

    val toolsManager = ToolsManager(_snackbarChannel)

    var isStarredScreen = false
    var pagingSource: PagingSource<Int, StorageItemListTuple>? = null
    var pagingStarredSource: PagingSource<Int, StorageItemListTuple>? = null

    val pagingFlow = Pager(
        PagingConfig(
            pageSize = AppConfig.PAGING_PAGE_SIZE,
            enablePlaceholders = AppConfig.PAGING_ENABLE_PLACEHOLDERS,
            initialLoadSize = AppConfig.PAGING_INITIAL_LOAD
        ),
        pagingSourceFactory = {
            repository.getList(
                parentDirectoryId = currentNavigatorDirectoryId,
                searchQuery = lastSearchQuery,
                dirIdsForSearch = searchDirsIndexesList,
                isNameEncrypted = encryptionInfo.isDatabaseEncrypted && encryptionInfo.isNameEncrypted
            ).also { pagingSource = it }
        }
    ).flow.map { repositoryEncryption.decryptPager(encryptionInfo, it) }.cachedIn(viewModelScope)

    val starredPagingFlow = Pager(
        PagingConfig(
            pageSize = AppConfig.PAGING_PAGE_SIZE,
            enablePlaceholders = AppConfig.PAGING_ENABLE_PLACEHOLDERS,
            initialLoadSize = AppConfig.PAGING_INITIAL_LOAD
        ),
        pagingSourceFactory = {
            repository.getStarredList(
                lastSearchQuery
            ).also { pagingStarredSource = it }
        }
    ).flow.map { repositoryEncryption.decryptPager(encryptionInfo, it) }.cachedIn(viewModelScope)

    val notesPagingFlow = Pager(
        PagingConfig(
            pageSize = 10,
            enablePlaceholders = false,
            initialLoadSize = 10
        ),
        pagingSourceFactory = { repository.getNotesList() }
    ).flow.map { pagingData ->
        val timePattern = "d MMMM yyyy"
        repositoryEncryption.decryptNotesPager(encryptionInfo, pagingData).map {
            Notes.Item(
                id = it.id,
                title = it.name,
                textPreview = it.textPreview?.run { "$this..." },
                creationTime = DateFormat.format(timePattern, it.creationTime).toString()
            )
        }
    }.cachedIn(viewModelScope)

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
            if (currentNavigatorDirectoryId != selectedNavigatorDir.id) {
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

    private fun triggerStarredListUpdate() {
        pagingStarredSource?.invalidate()
    }

    fun triggerFilesListUpdate() {
        pagingSource?.invalidate()
    }

    fun newDirectory(directoryName: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.newDirectory(
            encryptionInfo = encryptionInfo,
            directoryName = directoryName,
            parentDirectoryId = filesNavigatorList.lastOrNull()?.id
        )
        showSnackbar(R.string.snack_folderCreated)
    }

    fun delete(storageItemId: Long) = viewModelScope.launch(Dispatchers.IO) {
        val idsList = arrayListOf<Long>()
        val itemToDelete = repository.getMinimalItemData(encryptionInfo, storageItemId)
        suspend fun deleteIterator(itemToDelete: StorageItemMinimalTuple) {
            idsList.add(itemToDelete.id)
            if (itemToDelete.path.isNotEmpty()) {
                val localFile = io.getLocalFile(itemToDelete.path)
                if (localFile.exists()) {
                    val thumbLocalFile = io.getLocalFile(itemToDelete.thumb)
                    thumbLocalFile.delete()
                    localFile.delete()
                }
            } else repository.getMinimalItemsDataInDir(encryptionInfo, itemToDelete.id).forEach {
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
                val idToIterate = currentNavigatorDirectoryId
                if (idToIterate.toInt() != 0) withContext(Dispatchers.IO) {
                    searchDirsIndexesList = arrayListOf()
                    val array = searchDirsIndexesList!!
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
                            lastSearchQuery = it
                            triggerListUpdate()
                        }
                    }
            }
        } else {
            searchSetupJob?.cancel()
            searchSetupJob = null
            searchDirsIndexesList = null
            lastSearchQuery = null
            _searchChannel = null
            triggerListUpdate()
        }
    }

    fun searchQuerySubmit(query: String) {
        _searchChannel?.trySend(query)
    }

    fun isSearchActive() = searchSetupJob != null

    fun saveProfileInfo(
        profileInfo: ProfileInfo, force: Boolean = false
    ) = viewModelScope.launch(Dispatchers.IO) {
        /*val iconFile = io.getProfileIconFile()
        if (profileInfo.defaultAvatar == null) {
            if (_profileInfoFlow.value.iconFile != profileInfo.iconFile || force) {
                iconFile.recreate()
                val compressedByteStream = ByteArrayOutputStream().also {
                    profileInfo.iconFile!!.toBitmap().compress(Bitmap.CompressFormat.PNG, 98, it)
                }
                val thumbEncryption = encryptionInfo.thumbEncryptionOrdinal
                val keysetHandle = if (thumbEncryption != -1) {
                    keysetFactory.stream(KeysetTemplates.Stream.entries[thumbEncryption])
                } else null
                val outStream = keysetHandle?.streamingAeadPrimitive()?.newEncryptingStream(
                    iconFile.outputStream(),
                    keysetFactory.associatedData
                ) ?: iconFile.outputStream()
                outStream.use { it.write(compressedByteStream.toByteArray()) }
            }
        } else iconFile.delete()
        settingsDataStoreManager.setProfileInfo(profileInfo)
        _profileInfoFlow.update { profileInfo }*/
    }

    fun setupForFirstUse() {
        io.dataDir.mkdir()
        keysetFactory.associatedData
        viewModelScope.launch(Dispatchers.IO) {
            repository.createBasicFolders(Engine.appContext, encryptionInfo)
        }
    }

    fun loadPrivacyPolicy(activity: Activity) {
        if (privacyPolicyManager.privacyPolicyStateFlow.value == null) {
            viewModelScope.launch(Dispatchers.IO) { privacyPolicyManager.loadPrivacyPolicy(activity) }
        }
    }

    fun isDatabaseCreated() = io.dataDir.exists()

    private suspend fun showSnackbar(@StringRes stringId: Int) = _snackbarChannel.send(stringId)

    fun setUiState(uiStateOld: UiStateOld) {}

    fun getUiState() = UiStateOld()
    fun cacheUiState() {}

    fun restoreCachedUiState() = UiStateOld()

    fun invalidateCachedUiState() {}

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

}