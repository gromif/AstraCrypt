package io.gromif.astracrypt.files.data.util

import io.gromif.astracrypt.files.data.db.FilesEntity
import io.gromif.astracrypt.files.data.db.tuples.DeleteTuple
import io.gromif.astracrypt.files.data.db.tuples.DetailsTuple
import io.gromif.astracrypt.files.data.db.tuples.PagerTuple
import io.gromif.astracrypt.files.data.db.tuples.UpdateAeadTuple
import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.model.AeadMode

class AeadHandler(
    private val aeadUtil: AeadUtil,
) {

    suspend fun encryptFilesEntity(
        info: AeadInfo,
        mode: AeadMode.Template,
        data: FilesEntity,
    ): FilesEntity = data.copy(
        name = encryptNameIfNeeded(info, mode, data.name),
        file = mode.encryptIfNeeded(info.file, data.file),
        preview = mode.encryptIfNeeded(info.preview, data.preview),
        flags = mode.encryptIfNeeded(info.flag, data.flags),
    )

    suspend fun decryptFilesEntity(
        info: AeadInfo,
        mode: AeadMode.Template,
        data: FilesEntity,
    ): FilesEntity = data.copy(
        name = mode.decryptIfNeeded(info.name, data.name)!!,
        file = mode.decryptIfNeeded(info.file, data.file),
        preview = mode.decryptIfNeeded(info.preview, data.preview),
        flags = mode.decryptIfNeeded(info.flag, data.flags),
    )


    suspend fun encryptUpdateAeadTuple(
        info: AeadInfo,
        mode: AeadMode.Template,
        data: UpdateAeadTuple,
    ): UpdateAeadTuple = data.copy(
        name = encryptNameIfNeeded(info, mode, data.name),
        file = mode.encryptIfNeeded(info.file, data.file),
        preview = mode.encryptIfNeeded(info.preview, data.preview),
        flags = mode.encryptIfNeeded(info.flag, data.flags),
    )

    suspend fun decryptUpdateAeadTuple(
        info: AeadInfo,
        mode: AeadMode.Template,
        data: UpdateAeadTuple,
    ): UpdateAeadTuple = data.copy(
        name = mode.decryptIfNeeded(info.name, data.name)!!,
        file = mode.decryptIfNeeded(info.file, data.file),
        preview = mode.decryptIfNeeded(info.preview, data.preview),
        flags = mode.decryptIfNeeded(info.flag, data.flags),
    )


    suspend fun decryptPagerTuple(
        info: AeadInfo,
        mode: AeadMode.Template,
        data: PagerTuple,
    ) = data.copy(
        name = mode.decryptIfNeeded(info.name, data.name)!!,
        preview = mode.decryptIfNeeded(info.preview, data.preview),
    )

    suspend fun decryptDeleteTuple(
        info: AeadInfo,
        mode: AeadMode.Template,
        data: DeleteTuple,
    ) = data.copy(
        file = mode.decryptIfNeeded(info.file, data.file),
        preview = mode.decryptIfNeeded(info.preview, data.preview)
    )

    suspend fun decryptDetailsTuple(
        info: AeadInfo,
        mode: AeadMode.Template,
        data: DetailsTuple,
    ) = data.copy(
        name = mode.decryptIfNeeded(info.name, data.name)!!,
        file = mode.decryptIfNeeded(info.file, data.file),
        preview = mode.decryptIfNeeded(info.preview, data.preview),
        flags = mode.decryptIfNeeded(info.flag, data.flags),
    )


    suspend fun encryptNameIfNeeded(
        info: AeadInfo,
        mode: AeadMode.Template,
        data: String,
    ) = mode.encryptIfNeeded(info.name, data)!!

    private suspend fun AeadMode.Template.decryptIfNeeded(state: Boolean, data: String?): String? {
        return data?.let {
            if (state) aeadUtil.decrypt(aeadIndex = id, data) else data
        }
    }

    private suspend fun AeadMode.Template.encryptIfNeeded(state: Boolean, data: String?): String? {
        return data?.let {
            if (state) aeadUtil.encrypt(aeadIndex = id, data) else data
        }
    }

}