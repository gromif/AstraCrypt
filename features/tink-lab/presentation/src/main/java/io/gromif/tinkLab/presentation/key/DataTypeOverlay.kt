package io.gromif.tinkLab.presentation.key

import androidx.annotation.StringRes
import io.gromif.astracrypt.resources.R
import io.gromif.tinkLab.domain.model.DataType

@StringRes
fun DataType.titleStringId(): Int = when (this) {
    DataType.Files -> R.string.files
    DataType.Text -> R.string.text
}
