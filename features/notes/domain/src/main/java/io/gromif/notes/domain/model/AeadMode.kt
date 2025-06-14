package io.gromif.notes.domain.model

sealed class AeadMode(
    open val id: Int
) {

    object None: AeadMode(id = -1)

    data class Template(
        override val id: Int,
        val name: String,
    ): AeadMode(id = id)

}