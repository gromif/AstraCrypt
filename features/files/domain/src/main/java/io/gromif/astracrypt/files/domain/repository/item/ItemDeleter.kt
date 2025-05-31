package io.gromif.astracrypt.files.domain.repository.item

import io.gromif.astracrypt.files.domain.model.AeadInfo

interface ItemDeleter {

    suspend fun delete(aeadInfo: AeadInfo, id: Long)
}
