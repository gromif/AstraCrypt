package io.gromif.astracrypt.files.data.util.aead

import io.gromif.astracrypt.files.data.util.AeadUtil
import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.model.AeadMode

abstract class AbstractAeadHandler<T>(
    private val aeadUtil: AeadUtil,
) {

    abstract suspend fun encrypt(
        aeadInfo: AeadInfo,
        aeadTemplate: AeadMode.Template,
        data: T,
    ): T

    abstract suspend fun decrypt(
        aeadInfo: AeadInfo,
        aeadTemplate: AeadMode.Template,
        data: T,
    ): T

    protected suspend fun decryptIfNeeded(
        aeadTemplate: AeadMode.Template,
        currentState: Boolean,
        data: String?
    ): String? {
        return if (data != null && currentState) {
            aeadUtil.decrypt(aeadIndex = aeadTemplate.id, data)
        } else {
            data
        }
    }

    protected suspend fun encryptIfNeeded(
        aeadTemplate: AeadMode.Template,
        currentState: Boolean,
        data: String?
    ): String? {
        return if (data != null && currentState) {
            aeadUtil.encrypt(aeadIndex = aeadTemplate.id, data)
        } else {
            data
        }
    }
}
