package com.nevidimka655.tink_lab.data.repository

import com.nevidimka655.astracrypt.utils.Mapper
import com.nevidimka655.crypto.tink.domain.KeysetTemplates
import com.nevidimka655.tink_lab.data.dto.KeyDto
import com.nevidimka655.tink_lab.data.util.KeyFactory
import com.nevidimka655.tink_lab.domain.model.DataType
import com.nevidimka655.tink_lab.domain.model.Key
import com.nevidimka655.tink_lab.domain.model.Repository
import com.nevidimka655.tink_lab.domain.util.KeyWriter

private val keysetAssociatedData = "labKey".toByteArray()

class RepositoryImpl(
    private val keyFactory: KeyFactory,
    private val keyWriter: KeyWriter,
    private val dtoToKeyMapper: Mapper<KeyDto, Key>
) : Repository {
    override fun createKey(
        keysetPassword: String,
        dataType: DataType,
        aeadType: String
    ): Key {
        val keyDto = keyFactory.create(
            keysetPassword = keysetPassword,
            keysetAssociatedData = keysetAssociatedData,
            dataType = dataType,
            aeadType = aeadType
        )
        return dtoToKeyMapper(keyDto)
    }

    override fun save(key: Key, uriString: String) {
        keyWriter(
            uriString = uriString,
            key = key
        )
    }

    override fun getFileAeadList(): List<String> {
        return KeysetTemplates.Stream.entries
            .filter { it.name.endsWith("MB") }
            .map { it.name.removeSuffix("_1MB").lowercase() }
    }

    override fun getTextAeadList(): List<String> {
        return KeysetTemplates.AEAD.entries.map { it.name.lowercase() }
    }
}