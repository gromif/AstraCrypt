package io.gromif.astracrypt.files.domain.validation

internal sealed class ValidationException(message: String) : Exception(message) {

    data class InvalidNameException(override val message: String) : ValidationException(message)

    data class InvalidPathException(override val message: String) : ValidationException(message)

    class InvalidFileSizeException : ValidationException("File size cannot be negative.")

    class EmptyIdListException : ValidationException("Id list cannot be empty.")

}