package io.gromif.tink_lab.data.mapper

import io.gromif.astracrypt.utils.Mapper
import io.gromif.tinkLab.domain.model.DataType
import io.gromif.tinkLab.domain.model.Key
import io.gromif.tink_lab.data.dto.KeyDto

class DtoToKeyMapper(
    private val idToDataTypeMapper: Mapper<Int, DataType>
): Mapper<KeyDto, Key> {
    override fun invoke(item: KeyDto): Key = item.run {
        Key(
            dataType = idToDataTypeMapper(dataTypeId),
            aeadType = aeadType,
            rawKeyset = encryptedKeyset
        )
    }
}