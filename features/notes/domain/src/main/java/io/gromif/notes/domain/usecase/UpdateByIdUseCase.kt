package io.gromif.notes.domain.usecase

import io.gromif.notes.domain.repository.Repository

class UpdateByIdUseCase(
    private val getAeadPreferenceUseCase: GetAeadPreferenceUseCase,
    private val repository: Repository,
) {

    suspend operator fun invoke(id: Long, name: String, text: String) {
        val aead = getAeadPreferenceUseCase()
        repository.update(
            aead = aead,
            id = id,
            name = name,
            text = text
        )
    }

}