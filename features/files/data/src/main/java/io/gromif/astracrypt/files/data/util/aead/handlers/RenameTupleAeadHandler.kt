package io.gromif.astracrypt.files.data.util.aead.handlers

import io.gromif.astracrypt.files.data.db.tuples.RenameTuple
import io.gromif.astracrypt.files.data.util.AeadUtil
import io.gromif.astracrypt.files.data.util.aead.AbstractAeadHandler
import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.model.AeadMode

class RenameTupleAeadHandler(
    aeadUtil: AeadUtil
) : AbstractAeadHandler<RenameTuple>(aeadUtil = aeadUtil) {
    override suspend fun encrypt(
        aeadInfo: AeadInfo,
        aeadTemplate: AeadMode.Template,
        data: RenameTuple
    ): RenameTuple {
        throw NotImplementedError()
    }

    override suspend fun decrypt(
        aeadInfo: AeadInfo,
        aeadTemplate: AeadMode.Template,
        data: RenameTuple
    ): RenameTuple {
        return data.copy(
            name = decryptIfNeeded(
                aeadTemplate = aeadTemplate,
                currentState = aeadInfo.file,
                data = data.name
            )!!
        )
    }
}
