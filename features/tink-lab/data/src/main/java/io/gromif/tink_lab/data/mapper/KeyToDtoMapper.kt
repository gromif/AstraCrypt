package io.gromif.tink_lab.data.mapper

import io.gromif.astracrypt.utils.Mapper
import io.gromif.tinkLab.domain.model.DataType
import io.gromif.tinkLab.domain.model.Key
import io.gromif.tink_lab.data.dto.KeyDto

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