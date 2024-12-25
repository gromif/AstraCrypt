package com.nevidimka655.tink_lab.data.mapper

import com.nevidimka655.astracrypt.utils.Mapper
import com.nevidimka655.tink_lab.data.dto.KeyDto
import com.nevidimka655.tink_lab.domain.model.DataType
import com.nevidimka655.tink_lab.domain.model.Key

class KeyToDtoMapper(
    private val dataTypeToIdMapper: Mapper<DataType, Int>
): Mapper<Key, KeyDto> {
    override fun invoke(item: Key): KeyDto = item.run {
        KeyDto(
            dataTypeId = dataTypeToIdMapper(dataType),
            aeadType = aeadType,
            encryptedKeyset = rawKeyset
        )
    }
}