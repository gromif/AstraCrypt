package com.nevidimka655.astracrypt.view.composables.shared.export

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.nevidimka655.astracrypt.app.di.IoDispatcher
import com.nevidimka655.astracrypt.app.utils.AeadManager
import com.nevidimka655.astracrypt.app.utils.Io
import com.nevidimka655.astracrypt.app.work.ExportFilesWorker
import com.nevidimka655.astracrypt.data.model.ExportUiState
import com.nevidimka655.astracrypt.domain.repository.files.FilesRepository
import com.nevidimka655.crypto.tink.KeysetFactory
import com.nevidimka655.crypto.tink.extensions.toBase64
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.UUID
import javax.inject.Inject

private typealias Args = ExportFilesWorker.Args

@HiltViewModel
class ExportScreenViewModel @Inject constructor(
    @IoDispatcher
    private val defaultDispatcher: CoroutineDispatcher,
    private val aeadManager: AeadManager,
    private val filesRepository: FilesRepository,
    private val keysetFactory: KeysetFactory,
    val io: Io,
    val workManager: WorkManager
) : ViewModel() {
    private val workUUID = UUID.randomUUID()
    private var internalExportUri = ""
    var uiState by mutableStateOf(ExportUiState())

    fun export(
        itemId: Long, contentResolver: ContentResolver
    ) = viewModelScope.launch(defaultDispatcher) {
        val exportTuple = filesRepository.getDataForOpening(id = itemId)
        val exportFile = io.getExportedCacheFile(exportTuple.name)
        val outputUri = io.getExportedCacheFileUri(file = exportFile)
        internalExportUri = outputUri.toString()
        uiState = uiState.copy(name = exportTuple.name)
        val outStream = contentResolver.openOutputStream(outputUri)
        val inStream = io.getLocalFile(exportTuple.path).run {
            inputStream()
            /*if (exportTuple.encryptionType == -1) inputStream()
            else {
                val aeadTemplate = KeysetTemplates.Stream.entries[exportTuple.encryptionType]
                keysetFactory.stream(aeadTemplate).streamingAeadPrimitive()
                    .newDecryptingStream(inputStream(), keysetFactory.associatedData)
            }*/
        }
        inStream.use { ins ->
            outStream?.use { ous ->
                var bytesCopied: Long = 0
                val buffer = ByteArray(8192)
                var bytes = ins.read(buffer)
                while (bytes >= 0 && isActive) {
                    ous.write(buffer, 0, bytes)
                    bytesCopied += bytes
                    bytes = ins.read(buffer)
                }
            }
        }
        if (!isActive) exportFile.delete()
        else {
            delay(300)
            uiState = uiState.copy(isDone = true)
        }
    }

    fun export(itemId: Long, output: String) = viewModelScope.launch(defaultDispatcher) {
        val aeadInfo = aeadManager.getInfo()
        val associatedData = if (aeadInfo.bindAssociatedData)
            keysetFactory.transformAssociatedDataToWorkInstance(
                bytesIn = keysetFactory.associatedData,
                encryptionMode = true,
                authenticationTag = Args.TAG_ASSOCIATED_DATA_TRANSPORT
            ).toBase64()
        else null
        val aeadInfoJson = Json.encodeToString(aeadInfo)
        val data = workDataOf(
            Args.itemId to itemId,
            Args.aeadInfo to aeadInfoJson,
            Args.uriDirOutput to output,
            Args.associatedData to associatedData
        )
        val workerRequest = OneTimeWorkRequestBuilder<ExportFilesWorker>().apply {
            setId(workUUID)
            setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            setInputData(data)
        }.build()
        workManager.enqueue(workerRequest)
    }

    fun observeWorkInfoState() = viewModelScope.launch(defaultDispatcher) {
        workManager.getWorkInfoByIdFlow(id = workUUID).stateIn(this).collectLatest {
            it?.let { workInfo ->
                if (workInfo.state.isFinished) {
                    uiState = uiState.copy(isDone = true)
                    cancel()
                }
            }
        }
    }

    fun openExportedFile(context: Context) {
        val intentView = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(internalExportUri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        try {
            context.startActivity(intentView)
        } catch (_: Exception) {
        }
    }

    fun cancelExport() = workManager.cancelWorkById(id = workUUID)

    fun onDispose() = io.clearExportedCache()

}