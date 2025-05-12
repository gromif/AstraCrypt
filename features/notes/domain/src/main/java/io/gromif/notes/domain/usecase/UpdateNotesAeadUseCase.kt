package io.gromif.notes.domain.usecase

import io.gromif.notes.domain.model.AeadMode
import io.gromif.notes.domain.repository.Repository

class UpdateNotesAeadUseCase(
    private val getAeadPreferenceUseCase: GetAeadPreferenceUseCase,
    private val setAeadPreferenceUseCase: SetAeadPreferenceUseCase,
    private val repository: Repository,
) {

    suspend operator fun invoke(targetAeadMode: AeadMode) {
        val sourceAeadMode = getAeadPreferenceUseCase()
        repository.changeAead(
            sourceAeadMode = sourceAeadMode,
            targetAeadMode = targetAeadMode
        )
        setAeadPreferenceUseCase(targetAeadMode)
    }

}