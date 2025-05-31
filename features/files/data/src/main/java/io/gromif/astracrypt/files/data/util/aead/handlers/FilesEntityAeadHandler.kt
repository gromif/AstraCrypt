package io.gromif.astracrypt.files.data.util.aead.handlers

import io.gromif.astracrypt.files.data.db.FilesEntity
import io.gromif.astracrypt.files.data.util.AeadUtil
import io.gromif.astracrypt.files.data.util.aead.AbstractAeadHandler
import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.model.AeadMode

class FilesEntityAeadHandler(
    aeadUtil: AeadUtil
) : AbstractAeadHandler<FilesEntity>(aeadUtil = aeadUtil) {
    override suspend fun encrypt(
        aeadInfo: AeadInfo,
        aeadTemplate: AeadMode.Template,
        data: FilesEntity
    ): FilesEntity {
        return data.copy(
            name = encryptIfNeeded(
                aeadTemplate = aeadTemplate,
                currentState = aeadInfo.name,
                data = data.name
            )!!,
            file = encryptIfNeeded(
                aeadTemplate = aeadTemplate,
                currentState = aeadInfo.file,
                data = data.file
            ),
            preview = encryptIfNeeded(
                aeadTemplate = aeadTemplate,
                currentState = aeadInfo.preview,
                data = data.preview
            ),
            flags = encryptIfNeeded(
                aeadTemplate = aeadTemplate,
                currentState = aeadInfo.flag,
                data = data.flags
            ),
        )
    }

    override suspend fun decrypt(
        aeadInfo: AeadInfo,
        aeadTemplate: AeadMode.Template,
        data: FilesEntity
    ): FilesEntity {
        return data.copy(
            name = decryptIfNeeded(
                aeadTemplate = aeadTemplate,
                currentState = aeadInfo.name,
                data = data.name
            )!!,
            file = decryptIfNeeded(
                aeadTemplate = aeadTemplate,
                currentState = aeadInfo.file,
                data = data.file
            ),
            preview = decryptIfNeeded(
                aeadTemplate = aeadTemplate,
                currentState = aeadInfo.preview,
                data = data.preview
            ),
            flags = decryptIfNeeded(
                aeadTemplate = aeadTemplate,
                currentState = aeadInfo.flag,
                data = data.flags
            ),
        )
    }
}
