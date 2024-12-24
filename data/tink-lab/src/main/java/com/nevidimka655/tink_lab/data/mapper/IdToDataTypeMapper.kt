package com.nevidimka655.tink_lab.data.mapper

import com.nevidimka655.astracrypt.utils.Mapper
import com.nevidimka655.tink_lab.domain.model.DataType

class IdToDataTypeMapper: Mapper<Int, DataType> {
    override fun invoke(item: Int): DataType = item.run {
        DataType.entries[this]
    }
}