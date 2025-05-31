package io.gromif.astracrypt.files.details.parser.flags

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Headphones
import io.gromif.astracrypt.files.domain.model.FileFlags
import io.gromif.astracrypt.resources.R
import io.gromif.compose.details.addItem
import io.gromif.compose.details.model.DetailsItem
import io.gromif.ui.compose.core.wrappers.TextWrap

internal fun MutableList<DetailsItem>.addAudioFlags(flags: FileFlags.Audio) {
    addItem(
        icon = Icons.Outlined.Headphones,
        title = TextWrap.Resource(id = R.string.sample_rate),
        summary = TextWrap.Text(text = "${flags.sampleRate}kHz")
    )
}
