package io.gromif.astracrypt.files.data.util.aead.handlers

import io.gromif.astracrypt.files.data.db.tuples.DeleteTuple
import io.gromif.astracrypt.files.data.util.AeadUtil
import io.gromif.astracrypt.files.data.util.aead.AbstractAeadHandler
import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.model.AeadMode

class DeleteTupleAeadHandler(
    aeadUtil: AeadUtil
) : AbstractAeadHandler<DeleteTuple>(aeadUtil = aeadUtil) {
    override suspend fun encrypt(
        aeadInfo: AeadInfo,
        aeadTemplate: AeadMode.Template,
        data: DeleteTuple
    ): DeleteTuple {
        throw NotImplementedError()
    }

    override suspend fun decrypt(
        aeadInfo: AeadInfo,
        aeadTemplate: AeadMode.Template,
        data: DeleteTuple
    ): DeleteTuple {
        return data.copy(
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
        )
    }
}
