package io.gromif.notes.domain.usecase

import io.gromif.notes.domain.repository.Repository

class CreateUseCase(
    private val getAeadPreferenceUseCase: GetAeadPreferenceUseCase,
    private val repository: Repository,
) {

    suspend operator fun invoke(name: String, text: String) {
        val aead = getAeadPreferenceUseCase()
        repository.insert(aead, name, text)
    }

}