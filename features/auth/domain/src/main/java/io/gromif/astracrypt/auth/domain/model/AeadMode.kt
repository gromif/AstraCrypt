package io.gromif.astracrypt.auth.domain.model

sealed class AeadMode(open val id: Int) {

    data object None: AeadMode(id = -1)

    data class Template(override val id: Int): AeadMode(id = id)

}