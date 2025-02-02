package io.gromif.astracrypt.files.details.parser

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.nevidimka655.compose_details.addGroup
import com.nevidimka655.compose_details.model.DetailsGroup
import com.nevidimka655.ui.compose_core.wrappers.TextWrap
import io.gromif.astracrypt.files.details.parser.flags.addAudioFlags
import io.gromif.astracrypt.files.details.parser.flags.addImageFlags
import io.gromif.astracrypt.files.details.parser.flags.addVideoFlags
import io.gromif.astracrypt.files.domain.model.FileFlags
import io.gromif.astracrypt.files.domain.model.ItemType
import io.gromif.astracrypt.files.shared.title

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