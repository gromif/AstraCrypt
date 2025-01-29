package io.gromif.astracrypt.profile.domain.model

internal sealed class ValidationException(message: String) : Exception(message) {

    data class IllegalNameException(override val message: String) : ValidationException(message)

}