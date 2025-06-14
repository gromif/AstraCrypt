package io.gromif.notes.domain.usecase

import io.gromif.notes.domain.model.Note
import io.gromif.notes.domain.repository.Repository

class LoadByIdUseCase(
    private val getAeadPreferenceUseCase: GetAeadPreferenceUseCase,
    private val repository: Repository,
) {

    suspend operator fun invoke(id: Long): Note {
        val aead = getAeadPreferenceUseCase()
        return repository.getById(aead, id)
    }

}