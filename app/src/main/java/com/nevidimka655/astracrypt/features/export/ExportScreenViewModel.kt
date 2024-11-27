package com.nevidimka655.astracrypt.features.export

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nevidimka655.astracrypt.model.EncryptionInfo
import com.nevidimka655.astracrypt.model.ExportUiState
import com.nevidimka655.astracrypt.room.OpenTuple
import com.nevidimka655.astracrypt.room.Repository
import com.nevidimka655.astracrypt.room.StorageItemListTuple
import com.nevidimka655.astracrypt.utils.Engine
import com.nevidimka655.astracrypt.utils.IO
import com.nevidimka655.crypto.tink.KeysetFactory
import com.nevidimka655.crypto.tink.KeysetTemplates
import com.nevidimka655.crypto.tink.extensions.streamingAeadPrimitive
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ExportScreenViewModel : ViewModel() {
    private var dialogProgress = 0
    var uiState by mutableStateOf(ExportUiState())
    var selectedExportItem: StorageItemListTuple? = null
    var job: Job? = null

    fun openWithDialog(
        encryptionInfo: EncryptionInfo,
        itemId: Long
    ) {
        if (job == null) viewModelScope.launch(Dispatchers.IO) {
            openWithDialog(
                Repository.getDataForOpening(encryptionInfo = encryptionInfo, id = itemId)
            )
        }.also { job = it }
    }

    suspend fun openWithDialog(exportTuple: OpenTuple) {
        val exportFile = IO.getExportedCacheFile(exportTuple.name)
        val outputUri = FileProvider.getUriForFile(
            Engine.appContext,
            "com.nevidimka655.astracrypt",
            exportFile
        )
        uiState = uiState.copy(
            name = exportTuple.name,
            lastOutputFile = outputUri
        )
        val outStream = Engine.appContext.contentResolver.openOutputStream(outputUri)
        val inStream = IO.getLocalFile(exportTuple.path).run {
            if (exportTuple.encryptionType == -1) inputStream()
            else {
                val aeadTemplate = KeysetTemplates.Stream.entries[exportTuple.encryptionType]
                KeysetFactory.stream(Engine.appContext, aeadTemplate).streamingAeadPrimitive()
                    .newDecryptingStream(
                        inputStream(),
                        KeysetFactory.associatedData
                    )
            }
        }
        inStream.use { ins ->
            outStream?.use { ous ->
                var bytesCopied: Long = 0
                val buffer = ByteArray(8192)
                var bytes = ins.read(buffer)
                job?.let { jobSafe ->
                    while (bytes >= 0 && jobSafe.isActive) {
                        ous.write(buffer, 0, bytes)
                        bytesCopied += bytes
                        bytes = ins.read(buffer)
                    }
                }
            }
        }
        if (job?.isActive == false) exportFile.delete()
        else {
            delay(300)
            dialogProgress++
            uiState = uiState.copy(progress = dialogProgress)
        }
    }

}