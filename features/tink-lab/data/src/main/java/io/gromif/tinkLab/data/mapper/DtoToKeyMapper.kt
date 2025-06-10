package io.gromif.tinkLab.data.mapper

import io.gromif.astracrypt.utils.Mapper
import io.gromif.tinkLab.data.dto.KeyDto
import io.gromif.tinkLab.domain.model.DataType
import io.gromif.tinkLab.domain.model.Key

class DtoToKeyMapper(
    private val idToDataTypeMapper: Mapper<Int, DataType>
) : Mapper<KeyDto, Key> {
    override fun invoke(item: KeyDto): Key = item.run {
        Key(
            dataType = idToDataTypeMapper(dataTypeId),
            aeadType = aeadType,
            rawKeyset = encryptedKeyset
        )
    }
}
