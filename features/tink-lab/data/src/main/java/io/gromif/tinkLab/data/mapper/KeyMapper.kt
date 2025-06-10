package io.gromif.tinkLab.data.mapper

import io.gromif.tinkLab.data.dto.KeyDto
import io.gromif.tinkLab.domain.model.DataType
import io.gromif.tinkLab.domain.model.Key

internal fun Key.toDto() = KeyDto(
    dataTypeId = dataType.ordinal,
    aeadType = aeadType,
    encryptedKeyset = rawKeyset
)

internal fun KeyDto.toKey() = Key(
    dataType = DataType.entries[dataTypeId],
    aeadType = aeadType,
    rawKeyset = encryptedKeyset
)
