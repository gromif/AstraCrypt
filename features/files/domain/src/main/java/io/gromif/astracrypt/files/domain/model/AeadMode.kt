package io.gromif.astracrypt.files.domain.model

sealed class AeadMode(
    open val id: Int
) {

    object None : AeadMode(id = -1)

    data class Template(
        override val id: Int = 0,
        val name: String,
    ) : AeadMode(id = id)
}
