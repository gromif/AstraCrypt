package com.nevidimka655.astracrypt.features.export

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
import com.nevidimka655.astracrypt.model.ExportUiState
import com.nevidimka655.astracrypt.room.OpenTuple
import com.nevidimka655.astracrypt.room.Repository
import com.nevidimka655.astracrypt.utils.EncryptionManager
import com.nevidimka655.astracrypt.utils.Engine
import com.nevidimka655.astracrypt.utils.Io
import com.nevidimka655.astracrypt.work.ExportFilesWorker
import com.nevidimka655.crypto.tink.KeysetFactory
import com.nevidimka655.crypto.tink.KeysetTemplates
import com.nevidimka655.crypto.tink.extensions.streamingAeadPrimitive
import com.nevidimka655.crypto.tink.extensions.toBase64
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.UUID
import javax.inject.Inject

private typealias Args = ExportFilesWorker.Args

@HiltViewModel
class ExportScreenViewModel @Inject constructor(
    private val encryptionManager: EncryptionManager,
    private val repository: Repository,
    private val keysetFactory: KeysetFactory,
    val io: Io,
    val workManager: WorkManager
) : ViewModel() {
    private val workUUID = UUID.randomUUID()
    private var internalExportUri = ""
    var uiState by mutableStateOf(ExportUiState())

    fun export(itemId: Long) = viewModelScope.launch(Dispatchers.IO) {
        export(
            repository.getDataForOpening(id = itemId)
        )
    }

    fun export(itemId: Long, output: String) = viewModelScope.launch {
        val encryptionInfo = encryptionManager.getInfo()
        val associatedData = if (encryptionInfo.isAssociatedDataEncrypted)
            keysetFactory.transformAssociatedDataToWorkInstance(
                bytesIn = keysetFactory.associatedData,
                encryptionMode = true,
                authenticationTag = Args.TAG_ASSOCIATED_DATA_TRANSPORT
            ).toBase64()
        else null
        val encryptionInfoJson = Json.encodeToString(encryptionInfo)
        val data = workDataOf(
            Args.itemId to itemId,
            Args.encryptionInfo to encryptionInfoJson,
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

    suspend fun export(exportTuple: OpenTuple) = withContext(Dispatchers.IO) {
        val exportFile = io.getExportedCacheFile(exportTuple.name)
        val outputUri = io.getExportedCacheFileUri(file = exportFile)
        internalExportUri = outputUri.toString()
        uiState = uiState.copy(name = exportTuple.name)
        val outStream = Engine.appContext.contentResolver.openOutputStream(outputUri)
        val inStream = io.getLocalFile(exportTuple.path).run {
            if (exportTuple.encryptionType == -1) inputStream()
            else {
                val aeadTemplate = KeysetTemplates.Stream.entries[exportTuple.encryptionType]
                keysetFactory.stream(aeadTemplate).streamingAeadPrimitive()
                    .newDecryptingStream(inputStream(), keysetFactory.associatedData)
            }
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

    fun observeWorkInfoState() = viewModelScope.launch {
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