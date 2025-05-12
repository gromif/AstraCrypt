package io.gromif.tink_lab.presentation.key.saver

import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import io.gromif.tink_lab.domain.model.DataType

internal class DataTypeSaver : Saver<DataType, Int> {
    override fun restore(value: Int): DataType? {
        return DataType.entries[value]
    }

    override fun SaverScope.save(value: DataType): Int? {
        return value.ordinal
    }
}