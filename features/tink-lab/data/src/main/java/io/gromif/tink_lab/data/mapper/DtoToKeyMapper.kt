package io.gromif.tink_lab.data.mapper

import io.gromif.astracrypt.utils.Mapper
import io.gromif.tink_lab.data.dto.KeyDto
import io.gromif.tink_lab.domain.model.DataType
import io.gromif.tink_lab.domain.model.Key

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