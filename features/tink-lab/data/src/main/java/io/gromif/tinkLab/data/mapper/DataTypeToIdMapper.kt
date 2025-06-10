package io.gromif.tinkLab.data.mapper

import io.gromif.astracrypt.utils.Mapper
import io.gromif.tinkLab.domain.model.DataType

class DataTypeToIdMapper: Mapper<DataType, Int> {
    override fun invoke(item: DataType): Int = item.run {
        item.ordinal
    }
}