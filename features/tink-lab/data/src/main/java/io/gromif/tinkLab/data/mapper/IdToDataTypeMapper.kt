package io.gromif.tinkLab.data.mapper

import io.gromif.astracrypt.utils.Mapper
import io.gromif.tinkLab.domain.model.DataType

class IdToDataTypeMapper: Mapper<Int, DataType> {
    override fun invoke(item: Int): DataType = item.run {
        DataType.entries[this]
    }
}