package com.nevidimka655.astracrypt.app.services.utils

import androidx.lifecycle.LiveData
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.nevidimka655.crypto.tink.data.KeysetManager

class WorkerFactory(
    private val workManager: WorkManager,
    private val keysetManager: KeysetManager
) {
    var transformWorkLiveData: LiveData<WorkInfo?>? = null

    /*suspend fun startTransformDatabase(oldInfo: AeadInfo, newInfo: AeadInfo) {
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
    }*/

}