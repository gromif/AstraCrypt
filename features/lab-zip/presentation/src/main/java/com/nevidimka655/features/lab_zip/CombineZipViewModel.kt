package com.nevidimka655.features.lab_zip

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.google.crypto.tink.integration.android.AndroidKeystore
import com.nevidimka655.domain.lab_zip.FileInfo
import com.nevidimka655.domain.lab_zip.usecase.GetFileInfosUseCase
import com.nevidimka655.domain.lab_zip.usecase.GetSourceFileInfoUseCase
import com.nevidimka655.features.lab_zip.work.CombinedZipWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import io.gromif.astracrypt.utils.Mapper
import io.gromif.astracrypt.utils.dispatchers.IoDispatcher
import io.gromif.astracrypt.utils.io.WorkerSerializer
import io.gromif.crypto.tink.core.encoders.Base64Encoder
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CombineZipViewModel @Inject constructor(
    @IoDispatcher
    private val defaultDispatcher: CoroutineDispatcher,
    private val getSourceFileInfoUseCase: GetSourceFileInfoUseCase,
    private val getFileInfosUseCase: GetFileInfosUseCase,
    private val workManager: WorkManager,
    private val workerSerializer: WorkerSerializer,
    private val base64Encoder: Base64Encoder,
    private val uriToStringMapper: Mapper<Uri, String>
) : ViewModel() {
    val filesListState = mutableStateListOf<FileInfo>()
    var sourceState by mutableStateOf<FileInfo?>(null)

    fun startCombinedZipWorker(targetUri: Uri) = viewModelScope.launch(defaultDispatcher) {
        AndroidKeystore.generateNewAes256GcmKey(CombinedZipWorker.ANDROID_KEYSET_ALIAS)
        val dataAead = AndroidKeystore.getAead(CombinedZipWorker.ANDROID_KEYSET_ALIAS)
        val dataAD = CombinedZipWorker.ASSOCIATED_DATA.toByteArray()
        val contentUrisFile = workerSerializer.saveStringListToFile(filesListState.map { it.path })
        val contentUrisFileEncrypted = base64Encoder.encode(
            dataAead.encrypt(contentUrisFile.toString().toByteArray(), dataAD)
        )
        val zipFileUri = base64Encoder.encode(
            dataAead.encrypt(sourceState!!.path.toByteArray(), dataAD)
        )
        val targetUriString = uriToStringMapper(targetUri)
        val targetUri = base64Encoder.encode(dataAead.encrypt(targetUriString.toByteArray(), dataAD))
        val data = workDataOf(
            CombinedZipWorker.Args.ZIP_FILE_URI to contentUrisFileEncrypted,
            CombinedZipWorker.Args.SOURCE_URI to zipFileUri,
            CombinedZipWorker.Args.TARGET_URI to targetUri
        )
        val workerRequest = OneTimeWorkRequestBuilder<CombinedZipWorker>().apply {
            setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            setInputData(data)
        }.build()
        workManager.enqueue(workerRequest)
    }

    fun setSource(sourceUri: Uri) = viewModelScope.launch(defaultDispatcher) {
        sourceState = getSourceFileInfoUseCase(uriToStringMapper(sourceUri))
    }

    fun addFiles(uris: List<Uri>) = viewModelScope.launch(defaultDispatcher) {
        val paths = uris.map { uriToStringMapper(it) }
        filesListState.addAll(getFileInfosUseCase(paths))
    }

    fun resetSource() {
        sourceState = null
    }

    fun resetFiles() = filesListState.clear()

}