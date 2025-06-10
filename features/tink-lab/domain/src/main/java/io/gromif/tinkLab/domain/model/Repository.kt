package io.gromif.tinkLab.domain.model

interface Repository {

    fun getFileAeadList(): List<String>

    fun getTextAeadList(): List<String>
}
