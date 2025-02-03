package io.gromif.astracrypt.files.domain.usecase

import io.gromif.astracrypt.files.domain.model.ValidationRulesDto
import io.gromif.astracrypt.files.domain.validation.ValidationRules

class GetValidationRulesUsecase {

    operator fun invoke(): ValidationRulesDto {
        return ValidationRules.DTO_DEFAULT
    }

}