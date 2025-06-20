package io.gromif.astracrypt.files.details.parser.flags

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PhotoSizeSelectLarge
import io.gromif.astracrypt.files.domain.model.FileFlags
import io.gromif.astracrypt.resources.R
import io.gromif.compose.details.addItem
import io.gromif.compose.details.model.DetailsItem
import io.gromif.ui.compose.core.wrappers.TextWrap

internal fun MutableList<DetailsItem>.addVideoFlags(flags: FileFlags.Video) {
    addItem(
        icon = Icons.Outlined.PhotoSizeSelectLarge,
        title = TextWrap.Resource(id = R.string.videoResolution),
        summary = TextWrap.Text(text = flags.resolution)
    )
}
