package io.gromif.astracrypt.files.details.parser.flags

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PhotoSizeSelectLarge
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.compose_details.addItem
import com.nevidimka655.compose_details.model.DetailsItem
import com.nevidimka655.ui.compose_core.wrappers.TextWrap
import io.gromif.astracrypt.files.domain.model.FileFlags

internal fun MutableList<DetailsItem>.addVideoFlags(flags: FileFlags.Video) {
    addItem(
        icon = Icons.Outlined.PhotoSizeSelectLarge,
        title = TextWrap.Resource(id = R.string.videoResolution),
        summary = TextWrap.Text(text = flags.resolution)
    )
}