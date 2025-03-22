package io.gromif.astracrypt.profile.domain.usecase

import io.gromif.astracrypt.profile.domain.ValidationRules
import io.gromif.astracrypt.profile.domain.model.ValidationRulesDto

class GetValidationRulesUseCase {

    operator fun invoke(): ValidationRulesDto {
        return ValidationRulesDto(
            minNameLength = ValidationRules.MIN_NAME_LENGTH,
            maxNameLength = ValidationRules.MAX_NAME_LENGTH,
            iconSize = ValidationRules.SIZE_ICON,
            iconQuality = ValidationRules.QUALITY_ICON,
        )
    }

}