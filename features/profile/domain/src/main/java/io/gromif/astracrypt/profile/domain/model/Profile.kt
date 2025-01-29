package io.gromif.astracrypt.profile.domain.model

import io.gromif.astracrypt.profile.domain.ValidationRules

data class Profile(
    val name: String? = null,
    val avatar: Avatar = Avatar.Default(),
) {

    init {
        if (name != null) {
            require(name.isNotBlank()) {
                throw ValidationException.IllegalNameException("Name can't be blank!")
            }
            require(name.length >= ValidationRules.MIN_NAME_LENGTH && name.length <= ValidationRules.MAX_NAME_LENGTH) {
                throw ValidationException.IllegalNameException("Name must be between ${ValidationRules.MIN_NAME_LENGTH} and ${ValidationRules.MAX_NAME_LENGTH} characters!")
            }
        }
    }

}