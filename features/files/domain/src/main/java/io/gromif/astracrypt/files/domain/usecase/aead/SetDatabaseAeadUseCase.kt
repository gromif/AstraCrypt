package io.gromif.astracrypt.files.domain.usecase.aead

import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.repository.Repository

class SetDatabaseAeadUseCase(
    private val setAeadInfoUseCase: SetAeadInfoUseCase,
    private val getAeadInfoUseCase: GetAeadInfoUseCase,
    private val repository: Repository,
) {

    suspend operator fun invoke(targetAeadInfo: AeadInfo) {
        val currentAeadInfo = getAeadInfoUseCase()
        repository.changeAead(
            oldAeadInfo = currentAeadInfo,
            targetAeadInfo = targetAeadInfo
        )
        setAeadInfoUseCase(targetAeadInfo)
    }
}
