package io.gromif.astracrypt.files.domain.validation.validator

import io.gromif.astracrypt.files.domain.validation.ValidationException
import io.gromif.astracrypt.files.domain.validation.ValidationRules

internal object NameValidator {

    operator fun invoke(name: String) {
        require(name.isNotBlank()) {
            throw ValidationException.InvalidNameException("Name cannot be blank.")
        }

        val length = name.length
        val lengthRule = length >= ValidationRules.MIN_NAME_LENGTH &&
            length <= ValidationRules.MAX_NAME_LENGTH
        require(lengthRule) {
            throw ValidationException.InvalidNameException(
                "Name length must be between ${ValidationRules.MIN_NAME_LENGTH} " +
                    "and ${ValidationRules.MAX_NAME_LENGTH}. Received length: $length."
            )
        }
    }
}
