package io.gromif.astracrypt.files.data.util.aead.handlers

import io.gromif.astracrypt.files.data.db.tuples.PagerTuple
import io.gromif.astracrypt.files.data.util.AeadUtil
import io.gromif.astracrypt.files.data.util.aead.AbstractAeadHandler
import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.model.AeadMode

class PagerTupleAeadHandler(
    aeadUtil: AeadUtil
) : AbstractAeadHandler<PagerTuple>(aeadUtil = aeadUtil) {
    override suspend fun encrypt(
        aeadInfo: AeadInfo,
        aeadTemplate: AeadMode.Template,
        data: PagerTuple
    ): PagerTuple {
        throw NotImplementedError()
    }

    override suspend fun decrypt(
        aeadInfo: AeadInfo,
        aeadTemplate: AeadMode.Template,
        data: PagerTuple
    ): PagerTuple {
        return data.copy(
            name = decryptIfNeeded(
                aeadTemplate = aeadTemplate,
                currentState = aeadInfo.name,
                data = data.name
            )!!,
            preview = decryptIfNeeded(
                aeadTemplate = aeadTemplate,
                currentState = aeadInfo.preview,
                data = data.preview
            ),
        )
    }
}
