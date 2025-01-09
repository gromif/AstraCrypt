package io.gromif.astracrypt.settings.aead.domain.usecase

import io.gromif.astracrypt.settings.aead.domain.model.AeadTemplate
import io.gromif.astracrypt.settings.aead.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class GetNotesAeadTemplateUseCase(
    private val repository: Repository
) {

    operator fun invoke(): Flow<AeadTemplate> {
        return repository.getNotesAeadTemplateFlow()
    }

}