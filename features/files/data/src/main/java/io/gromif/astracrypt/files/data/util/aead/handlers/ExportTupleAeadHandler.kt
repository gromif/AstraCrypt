package io.gromif.astracrypt.files.data.util.aead.handlers

import io.gromif.astracrypt.files.data.db.tuples.ExportTuple
import io.gromif.astracrypt.files.data.util.AeadUtil
import io.gromif.astracrypt.files.data.util.aead.AbstractAeadHandler
import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.model.AeadMode

class ExportTupleAeadHandler(
    aeadUtil: AeadUtil
) : AbstractAeadHandler<ExportTuple>(aeadUtil = aeadUtil) {
    override suspend fun encrypt(
        aeadInfo: AeadInfo,
        aeadTemplate: AeadMode.Template,
        data: ExportTuple
    ): ExportTuple {
        throw NotImplementedError()
    }

    override suspend fun decrypt(
        aeadInfo: AeadInfo,
        aeadTemplate: AeadMode.Template,
        data: ExportTuple
    ): ExportTuple {
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
        )
    }
}
