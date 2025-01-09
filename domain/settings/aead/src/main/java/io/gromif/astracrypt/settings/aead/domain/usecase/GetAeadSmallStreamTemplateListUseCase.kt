package io.gromif.astracrypt.settings.aead.domain.usecase

import io.gromif.astracrypt.settings.aead.domain.model.AeadTemplate
import io.gromif.astracrypt.settings.aead.domain.repository.Repository

class GetAeadSmallStreamTemplateListUseCase(
    private val repository: Repository
) {

    operator fun invoke(): List<AeadTemplate> {
        return repository.getAeadSmallStreamTemplateList()
    }

}