package io.gromif.tink_lab.data.mapper

import io.gromif.astracrypt.utils.Mapper
import io.gromif.tink_lab.data.dto.KeyDto
import io.gromif.tink_lab.domain.model.DataType
import io.gromif.tink_lab.domain.model.Key

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