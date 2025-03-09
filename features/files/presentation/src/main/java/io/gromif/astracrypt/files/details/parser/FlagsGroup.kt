package io.gromif.astracrypt.files.details.parser

import androidx.compose.runtime.snapshots.SnapshotStateList
import io.gromif.astracrypt.files.details.parser.flags.addAudioFlags
import io.gromif.astracrypt.files.details.parser.flags.addImageFlags
import io.gromif.astracrypt.files.details.parser.flags.addVideoFlags
import io.gromif.astracrypt.files.domain.model.FileFlags
import io.gromif.astracrypt.files.domain.model.ItemType
import io.gromif.astracrypt.files.shared.title
import io.gromif.compose.details.addGroup
import io.gromif.compose.details.model.DetailsGroup
import io.gromif.ui.compose.core.wrappers.TextWrap

internal fun SnapshotStateList<DetailsGroup>.addFlagsGroup(
    type: ItemType,
    flags: FileFlags?
) {
    if (flags == null) return
    addGroup(name = TextWrap.Resource(id = type.title)) {
        when (flags) {
            is FileFlags.Audio -> addAudioFlags(flags)
            is FileFlags.Image -> addImageFlags(flags)
            is FileFlags.Video -> addVideoFlags(flags)
        }
    }
}