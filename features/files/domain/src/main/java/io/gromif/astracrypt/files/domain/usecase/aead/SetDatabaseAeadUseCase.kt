package io.gromif.astracrypt.files.domain.usecase.aead

import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.repository.AeadManager

class SetDatabaseAeadUseCase(
    private val setAeadInfoUseCase: SetAeadInfoUseCase,
    private val getAeadInfoUseCase: GetAeadInfoUseCase,
    private val aeadManager: AeadManager,
) {

    suspend operator fun invoke(targetAeadInfo: AeadInfo) {
        val currentAeadInfo = getAeadInfoUseCase()
        aeadManager.changeAead(
            oldAeadInfo = currentAeadInfo,
            targetAeadInfo = targetAeadInfo
        )
        setAeadInfoUseCase(targetAeadInfo)
    }
}
