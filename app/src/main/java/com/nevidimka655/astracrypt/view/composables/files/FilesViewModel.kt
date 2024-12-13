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
import com.nevidimka655.astracrypt.app.extensions.removeLines
import com.nevidimka655.astracrypt.app.utils.AeadManager
import com.nevidimka655.astracrypt.app.utils.Io
import com.nevidimka655.astracrypt.app.work.ImportFilesWorker
import com.nevidimka655.astracrypt.app.work.utils.WorkerSerializer
import com.nevidimka655.astracrypt.data.datastore.AppearanceManager
import com.nevidimka655.astracrypt.data.repository.RepositoryProvider
import com.nevidimka655.astracrypt.domain.room.PagerTuple
import com.nevidimka655.crypto.tink.KeysetFactory
import com.nevidimka655.crypto.tink.extensions.toBase64
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
    private val repositoryProvider: RepositoryProvider,
    private val keysetFactory: KeysetFactory,
    private val aeadManager: AeadManager,
    val appearanceManager: AppearanceManager,
    val io: Io,
    val workManager: WorkManager,
    val workerSerializer: WorkerSerializer,
    val imageLoader: ImageLoader
) : ViewModel() {
    private var cameraScanOutputUri: Uri? = null

    var optionsItem by mutableStateOf(PagerTuple())

    val sheetOptionsState = mutableStateOf(false)
    val dialogRenameState = mutableStateOf(false)
    val dialogDeleteState = mutableStateOf(false)

    fun getCameraScanOutputUri() = io.getExportedCacheFileUri(
        file = io.getExportedCacheCameraFile()
    ).also { cameraScanOutputUri = it }

    fun setStarredFlag(
        state: Boolean,
        id: Long? = null,
        itemsArr: List<Long>? = null
    ) = viewModelScope.launch(Dispatchers.IO) {
        repositoryProvider.repository.first().setStarred(id, itemsArr, state)
        /*showSnackbar(
            if (state) R.string.snack_starred else R.string.snack_unstarred
        )*/ // TODO: Snackbar
    }

    fun import(
        vararg uriList: Uri,
        saveOriginalFiles: Boolean = false,
        dirId: Long
    ) = viewModelScope.launch {
        val aeadInfo = aeadManager.getInfo()
        val listToSave = uriList.map { it.toString() }.toTypedArray()
        val fileWithUris = workerSerializer.saveStringArrayToFile(listToSave)
        val aeadInfoJson = Json.encodeToString(aeadInfo)
        val associatedData = if (aeadInfo.isAssociatedDataEncrypted)
            keysetFactory.transformAssociatedDataToWorkInstance(
                bytesIn = keysetFactory.associatedData,
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

    fun rename(id: Long, name: String) = viewModelScope.launch(Dispatchers.IO) {
        val newName = name.removeLines().trim()
        val repository = repositoryProvider.repository.first()
        repository.updateName(id, newName)
        //showSnackbar(R.string.snack_itemRenamed) TODO: Snackbar
    }

    fun importCameraScan(dirId: Long) = import(
        cameraScanOutputUri!!,
        dirId = dirId
    )

}