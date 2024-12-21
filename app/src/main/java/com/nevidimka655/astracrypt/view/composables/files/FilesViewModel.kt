package com.nevidimka655.astracrypt.view.composables.files

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import coil.ImageLoader
import com.nevidimka655.astracrypt.app.di.IoDispatcher
import com.nevidimka655.astracrypt.data.crypto.AeadManager
import com.nevidimka655.astracrypt.data.io.FilesService
import com.nevidimka655.astracrypt.app.services.ImportFilesWorker
import com.nevidimka655.astracrypt.app.services.utils.WorkerSerializer
import com.nevidimka655.astracrypt.data.repository.RepositoryProviderImpl
import com.nevidimka655.astracrypt.data.database.PagerTuple
import com.nevidimka655.crypto.tink.data.KeysetManager
import com.nevidimka655.crypto.tink.extensions.toBase64
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

private typealias Args = ImportFilesWorker.Args

@HiltViewModel
class FilesViewModel @Inject constructor(
    @IoDispatcher
    private val defaultDispatcher: CoroutineDispatcher,
    private val repositoryProviderImpl: RepositoryProviderImpl,
    private val keysetManager: KeysetManager,
    private val aeadManager: AeadManager,
    val filesService: FilesService,
    val workManager: WorkManager,
    val workerSerializer: WorkerSerializer,
    val imageLoader: ImageLoader
) : ViewModel() {
    private var cameraScanOutputUri: Uri? = null

    var optionsItem by mutableStateOf(PagerTuple())

    val sheetOptionsState = mutableStateOf(false)
    val dialogRenameState = mutableStateOf(false)
    val dialogDeleteState = mutableStateOf(false)

    fun getCameraScanOutputUri() = filesService.getExportedCacheFileUri(
        file = filesService.getExportedCacheCameraFile()
    ).also { cameraScanOutputUri = it }

    fun setStarredFlag(
        state: Boolean,
        id: Long? = null,
        itemsArr: List<Long>? = null
    ) = viewModelScope.launch(defaultDispatcher) {
        repositoryProviderImpl.repository.first().setStarred(id, itemsArr, state)
        /*showSnackbar(
            if (state) R.string.snack_starred else R.string.snack_unstarred
        )*/ // TODO: Snackbar
    }

    fun import(
        vararg uriList: Uri,
        saveOriginalFiles: Boolean = false,
        dirId: Long
    ) = viewModelScope.launch(defaultDispatcher) {
        val aeadInfo = aeadManager.getInfo()
        val listToSave = uriList.map { it.toString() }.toTypedArray()
        val fileWithUris = workerSerializer.saveStringArrayToFile(listToSave)
        val aeadInfoJson = Json.encodeToString(aeadInfo)
        val associatedData = if (aeadInfo.bindAssociatedData)
            keysetManager.transformAssociatedDataToWorkInstance(
                bytesIn = keysetManager.associatedData,
                encryptionMode = true,
                authenticationTag = Args.TAG_ASSOCIATED_DATA_TRANSPORT
            ).toBase64()
        else null
        val data = workDataOf(
            Args.fileWithUris to fileWithUris,
            Args.dirId to dirId,
            Args.saveOriginalFiles to saveOriginalFiles,
            Args.aeadInfo to aeadInfoJson,
            Args.associatedData to associatedData
        )
        val workerRequest = OneTimeWorkRequestBuilder<ImportFilesWorker>().apply {
            setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            setInputData(data)
        }.build()
        workManager.enqueue(workerRequest)
        workManager.getWorkInfoByIdFlow(workerRequest.id).collectLatest {
            when (it?.state) {
                WorkInfo.State.SUCCEEDED -> {
//                    showSnackbar(R.string.snack_imported) TODO: Snackbar
                    cancel()
                }

                WorkInfo.State.FAILED -> {
                    cancel()
                }

                else -> {}
            }
        }
    }

    fun rename(id: Long, name: String) = viewModelScope.launch(defaultDispatcher) {
        val newName = name.trim()
        val repository = repositoryProviderImpl.repository.first()
        repository.updateName(id, newName)
        //showSnackbar(R.string.snack_itemRenamed) TODO: Snackbar
    }

    fun importCameraScan(dirId: Long) = import(
        cameraScanOutputUri!!,
        dirId = dirId
    )

}