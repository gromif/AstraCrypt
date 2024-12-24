package com.nevidimka655.tink_lab.domain.model

interface Repository {

    fun getFileAeadList() : List<String>

    fun getTextAeadList() : List<String>

}