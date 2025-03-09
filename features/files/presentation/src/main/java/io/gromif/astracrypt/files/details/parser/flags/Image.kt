package io.gromif.astracrypt.files.details.parser.flags

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PhotoSizeSelectLarge
import com.nevidimka655.astracrypt.resources.R
import io.gromif.astracrypt.files.domain.model.FileFlags
import io.gromif.compose.details.addItem
import io.gromif.compose.details.model.DetailsItem
import io.gromif.ui.compose.core.wrappers.TextWrap

internal fun MutableList<DetailsItem>.addImageFlags(flags: FileFlags.Image) {
    addItem(
        icon = Icons.Outlined.PhotoSizeSelectLarge,
        title = TextWrap.Resource(id = R.string.imageResolution),
        summary = TextWrap.Text(text = flags.resolution)
    )
}