package com.nevidimka655.astracrypt.work.utils

import android.annotation.SuppressLint
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkInfo
import androidx.work.workDataOf
import com.nevidimka655.astracrypt.model.EncryptionInfo
import com.nevidimka655.astracrypt.utils.Engine
import com.nevidimka655.astracrypt.utils.Io
import com.nevidimka655.astracrypt.work.LabCombinedZipWorker
import com.nevidimka655.astracrypt.work.TransformDatabaseWorker
import com.nevidimka655.astracrypt.work.TransformNotesWorker
import com.nevidimka655.crypto.tink.KeysetFactory
import com.nevidimka655.crypto.tink.extensions.toBase64
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object WorkerFactory {
    private val worker get() = Engine.workManager
    @SuppressLint("StaticFieldLeak")
    private val io = Io(Engine.appContext) // TODO: Replace with DI
    var transformWorkLiveData: LiveData<WorkInfo?>? = null

    fun startTransformDatabase(oldInfo: EncryptionInfo, newInfo: EncryptionInfo) {
        val associatedData = if (newInfo.isAssociatedDataEncrypted)
            KeysetFactory.transformAssociatedDataToWorkInstance(
                context = Engine.appContext,
                bytesIn = KeysetFactory.associatedData,
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
        worker.enqueue(workerRequest)
        transformWorkLiveData = worker.getWorkInfoByIdLiveData(workerRequest.id)
    }

    fun startTransformNotes(oldInfo: EncryptionInfo, newInfo: EncryptionInfo) {
        val associatedData = if (newInfo.isAssociatedDataEncrypted)
            KeysetFactory.transformAssociatedDataToWorkInstance(
                context = Engine.appContext,
                bytesIn = KeysetFactory.associatedData,
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
        worker.enqueue(workerRequest)
        transformWorkLiveData = worker.getWorkInfoByIdLiveData(workerRequest.id)
    }

    suspend fun startCombinedZipWorker(
        sourceUri: Uri,
        destinationUri: Uri,
        zipFilesContentStringArray: Array<String>
    ) {
        val fileWithZipContentPath =
            WorkerSerializer(io).saveStringArrayToFile(zipFilesContentStringArray)
        val data = workDataOf(
            Pair(LabCombinedZipWorker.Args.ZIP_FILE_URI, fileWithZipContentPath),
            Pair(LabCombinedZipWorker.Args.SOURCE_URI, sourceUri.toString()),
            Pair(LabCombinedZipWorker.Args.TARGET_URI, destinationUri.toString())
        )
        val workerRequest = OneTimeWorkRequestBuilder<LabCombinedZipWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setInputData(data)
            .build()
        worker.enqueue(workerRequest)
    }

}