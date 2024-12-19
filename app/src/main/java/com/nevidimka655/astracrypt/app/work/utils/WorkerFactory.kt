package com.nevidimka655.astracrypt.app.work.utils

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.nevidimka655.astracrypt.app.work.LabCombinedZipWorker
import com.nevidimka655.astracrypt.app.work.TransformDatabaseWorker
import com.nevidimka655.astracrypt.app.work.TransformNotesWorker
import com.nevidimka655.astracrypt.data.model.AeadInfo
import com.nevidimka655.crypto.tink.data.KeysetManager
import com.nevidimka655.crypto.tink.extensions.toBase64
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class WorkerFactory(
    private val workManager: WorkManager,
    private val workerSerializer: WorkerSerializer,
    private val keysetManager: KeysetManager
) {
    var transformWorkLiveData: LiveData<WorkInfo?>? = null

    suspend fun startTransformDatabase(oldInfo: AeadInfo, newInfo: AeadInfo) {
        val associatedData = if (newInfo.bindAssociatedData)
            keysetManager.transformAssociatedDataToWorkInstance(
                bytesIn = keysetManager.associatedData,
                encryptionMode = true,
                authenticationTag = TransformDatabaseWorker.Args.TAG_ASSOCIATED_DATA_TRANSPORT
            ).toBase64()
        else null
        val data = workDataOf(
            Pair(TransformDatabaseWorker.Args.oldEncryptionInfo, Json.encodeToString(oldInfo)),
            Pair(TransformDatabaseWorker.Args.newEncryptionInfo, Json.encodeToString(newInfo)),
            Pair(TransformDatabaseWorker.Args.associatedData, associatedData)
        )
        val workerRequest = OneTimeWorkRequestBuilder<TransformDatabaseWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setInputData(data)
            .build()
        workManager.enqueue(workerRequest)
        transformWorkLiveData = workManager.getWorkInfoByIdLiveData(workerRequest.id)
    }

    suspend fun startTransformNotes(oldInfo: AeadInfo, newInfo: AeadInfo) {
        val associatedData = if (newInfo.bindAssociatedData)
            keysetManager.transformAssociatedDataToWorkInstance(
                bytesIn = keysetManager.associatedData,
                encryptionMode = true,
                authenticationTag = TransformNotesWorker.Args.TAG_ASSOCIATED_DATA_TRANSPORT
            ).toBase64()
        else null
        val data = workDataOf(
            Pair(TransformNotesWorker.Args.oldEncryptionInfo, Json.encodeToString(oldInfo)),
            Pair(TransformNotesWorker.Args.newEncryptionInfo, Json.encodeToString(newInfo)),
            Pair(TransformNotesWorker.Args.associatedData, associatedData)
        )
        val workerRequest = OneTimeWorkRequestBuilder<TransformNotesWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setInputData(data)
            .build()
        workManager.enqueue(workerRequest)
        transformWorkLiveData = workManager.getWorkInfoByIdLiveData(workerRequest.id)
    }

    suspend fun startCombinedZipWorker(
        sourceUri: Uri,
        destinationUri: Uri,
        zipFilesContentStringArray: Array<String>
    ) {
        val fileWithZipContentPath =
            workerSerializer.saveStringArrayToFile(zipFilesContentStringArray)
        val data = workDataOf(
            Pair(LabCombinedZipWorker.Args.ZIP_FILE_URI, fileWithZipContentPath),
            Pair(LabCombinedZipWorker.Args.SOURCE_URI, sourceUri.toString()),
            Pair(LabCombinedZipWorker.Args.TARGET_URI, destinationUri.toString())
        )
        val workerRequest = OneTimeWorkRequestBuilder<LabCombinedZipWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setInputData(data)
            .build()
        workManager.enqueue(workerRequest)
    }

}