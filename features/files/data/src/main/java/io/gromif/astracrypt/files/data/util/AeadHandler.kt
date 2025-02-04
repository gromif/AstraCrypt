package io.gromif.astracrypt.files.data.util

import io.gromif.astracrypt.files.data.db.FilesEntity
import io.gromif.astracrypt.files.data.db.tuples.DeleteTuple
import io.gromif.astracrypt.files.data.db.tuples.DetailsTuple
import io.gromif.astracrypt.files.domain.model.AeadInfo

class AeadHandler(
    private val aeadUtil: AeadUtil,
) {

    suspend fun encryptFilesEntity(info: AeadInfo, data: FilesEntity) = data.copy(
        name = encryptNameIfNeeded(info, data.name),
        file = info.encryptIfNeeded(info.file, data.file),
        preview = info.encryptIfNeeded(info.preview, data.preview),
        flags = info.encryptIfNeeded(info.flag, data.flags),
    )

    suspend fun decryptFilesEntity(info: AeadInfo, data: FilesEntity) = data.copy(
        name = info.decryptIfNeeded(info.name, data.name)!!,
        file = info.decryptIfNeeded(info.file, data.file),
        preview = info.decryptIfNeeded(info.preview, data.preview),
        flags = info.decryptIfNeeded(info.flag, data.flags),
    )

    suspend fun decryptDeleteTuple(info: AeadInfo, data: DeleteTuple) = data.copy(
        file = info.decryptIfNeeded(info.file, data.file),
        preview = info.decryptIfNeeded(info.preview, data.preview)
    )

    suspend fun decryptDetailsTuple(info: AeadInfo, data: DetailsTuple) = data.copy(
        name = info.decryptIfNeeded(info.name, data.name)!!,
        file = info.decryptIfNeeded(info.file, data.file),
        preview = info.decryptIfNeeded(info.preview, data.preview),
        flags = info.decryptIfNeeded(info.flag, data.flags),
    )


    suspend fun encryptNameIfNeeded(info: AeadInfo, data: String) =
        info.encryptIfNeeded(info.name, data)!!

    private suspend fun AeadInfo.decryptIfNeeded(state: Boolean, data: String?): String? {
        return data?.let {
            if (state) aeadUtil.decrypt(aeadIndex = databaseAeadIndex, data) else data
        }
    }

    private suspend fun AeadInfo.encryptIfNeeded(state: Boolean, data: String?): String? {
        return data?.let {
            if (state) aeadUtil.encrypt(aeadIndex = databaseAeadIndex, data) else data
        }
    }

}