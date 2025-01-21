package io.gromif.astracrypt.files.domain.util

interface AeadUtil {

    suspend fun decrypt(aeadIndex: Int, data: String): String

    suspend fun encrypt(aeadIndex: Int, data: String): String

}