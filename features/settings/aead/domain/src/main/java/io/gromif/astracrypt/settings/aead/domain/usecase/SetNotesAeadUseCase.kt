package io.gromif.astracrypt.settings.aead.domain.usecase

import io.gromif.astracrypt.settings.aead.domain.repository.Repository

class SetNotesAeadUseCase(
    private val repository: Repository
) {

    suspend operator fun invoke(aead: Int) {
        return repository.setNotesAeadTemplateIndex(aead = aead)
    }

}