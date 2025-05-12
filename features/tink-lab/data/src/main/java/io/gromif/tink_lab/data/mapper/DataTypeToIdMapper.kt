package io.gromif.tink_lab.data.mapper

import io.gromif.astracrypt.utils.Mapper
import io.gromif.tink_lab.domain.model.DataType

class DataTypeToIdMapper: Mapper<DataType, Int> {
    override fun invoke(item: DataType): Int = item.run {
        item.ordinal
    }
}