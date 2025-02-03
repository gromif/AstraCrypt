package io.gromif.astracrypt.files.domain.validation

import io.gromif.astracrypt.files.domain.model.ValidationRulesDto

internal object ValidationRules {

    const val MIN_NAME_LENGTH = 1
    const val MAX_NAME_LENGTH = 128
    const val MAX_BACKSTACK_NAME_LENGTH = 80

    val DTO_DEFAULT = ValidationRulesDto(
        minNameLength = MIN_NAME_LENGTH,
        maxNameLength = MAX_NAME_LENGTH,
        maxBackstackNameLength = MAX_BACKSTACK_NAME_LENGTH,
    )

}