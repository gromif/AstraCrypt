package com.nevidimka655.astracrypt.ui.tabs.files

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkInfo
import androidx.work.WorkManager
import coil.ImageLoader
import com.nevidimka655.astracrypt.model.EncryptionInfo
import com.nevidimka655.astracrypt.room.Repository
import com.nevidimka655.astracrypt.room.StorageItemListTuple
import com.nevidimka655.astracrypt.utils.Engine
import com.nevidimka655.astracrypt.utils.IO
import com.nevidimka655.astracrypt.work.ImportFilesWorker
import com.nevidimka655.astracrypt.work.utils.WorkerSerializer
import com.nevidimka655.crypto.tink.KeysetFactory
import com.nevidimka655.crypto.tink.extensions.toBase64
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class FilesViewModel @Inject constructor(
    val workManager: WorkManager,
    val imageLoader: ImageLoader
) : ViewModel() {
    private var cameraScanOutputUri: Uri? = null

    var optionsItem by mutableStateOf(StorageItemListTuple())

    val sheetOptionsState = mutableStateOf(false)
    val dialogRenameState = mutableStateOf(false)
    val dialogDeleteState = mutableStateOf(false)

    fun getCameraScanOutputUri(context: Context) = FileProvider.getUriForFile(
        context,
        "com.nevidimka655.astracrypt",
        IO.getExportedCacheCameraFile()
    ).also { cameraScanOutputUri = it }

    fun setStarredFlag(
        state: Boolean,
        id: Long? = null,
        itemsArr: List<Long>? = null
    ) = viewModelScope.launch(Dispatchers.IO) {
        Repository.setStarred(id, itemsArr, state)
        /*showSnackbar(
            if (state) R.string.snack_starred else R.string.snack_unstarred
        )*/ // TODO: Snackbar
    }

    fun import(
        vararg uriList: Uri,
        saveOriginalFiles: Boolean = false,
        dirId: Long,
        encryptionInfo: EncryptionInfo
    ) = viewModelScope.launch {
        val listToSave = uriList.map { it.toString() }.toTypedArray()
        val fileWithUris = WorkerSerializer.saveStringArrayToFile(listToSave)
        val data = Data.Builder().apply {
            putString(ImportFilesWorker.Args.fileWithUris, fileWithUris)
            putLong(ImportFilesWorker.Args.dirId, dirId)
            putBoolean(ImportFilesWorker.Args.saveOriginalFiles, saveOriginalFiles)
            putString(ImportFilesWorker.Args.encryptionInfo, Json.encodeToString(encryptionInfo))
            putString(
                ImportFilesWorker.Args.associatedData,
                if (encryptionInfo.isAssociatedDataEncrypted)
                    KeysetFactory.transformAssociatedDataToWorkInstance(
                        context = Engine.appContext,
                        bytesIn = KeysetFactory.associatedData,
                        encryptionMode = true,
                        authenticationTag = ImportFilesWorker.Args.TAG_ASSOCIATED_DATA_TRANSPORT
                    ).toBase64()
                else null
            )
        }.build()
        val workerRequest = OneTimeWorkRequestBuilder<ImportFilesWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setInputData(data)
            .build()
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

    fun importCameraScan(dirId: Long, encryptionInfo: EncryptionInfo) = import(
        cameraScanOutputUri!!,
        dirId = dirId,
        encryptionInfo = encryptionInfo
    )

}