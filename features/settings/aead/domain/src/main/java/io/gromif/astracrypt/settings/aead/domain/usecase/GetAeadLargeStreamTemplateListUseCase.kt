package io.gromif.astracrypt.settings.aead.domain.usecase

import io.gromif.astracrypt.settings.aead.domain.model.AeadTemplate
import io.gromif.astracrypt.settings.aead.domain.repository.Repository

class GetAeadLargeStreamTemplateListUseCase(
    private val repository: Repository
) {

    operator fun invoke(): List<AeadTemplate> {
        return repository.getAeadLargeStreamTemplateList()
    }

}