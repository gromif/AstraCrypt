package com.nevidimka655.tink_lab.data.mapper

import com.nevidimka655.astracrypt.utils.Mapper
import com.nevidimka655.tink_lab.domain.model.DataType

class DataTypeToIdMapper: Mapper<DataType, Int> {
    override fun invoke(item: DataType): Int = item.run {
        item.ordinal
    }
}