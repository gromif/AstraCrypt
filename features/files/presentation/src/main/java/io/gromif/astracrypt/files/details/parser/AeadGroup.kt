package io.gromif.astracrypt.files.details.parser

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.compose_details.addGroup
import com.nevidimka655.compose_details.addItem
import com.nevidimka655.compose_details.model.DetailsGroup
import io.gromif.crypto.tink.model.KeysetTemplates
import io.gromif.ui.compose.core.wrappers.TextWrap

internal fun SnapshotStateList<DetailsGroup>.addAeadGroup(
    fileAead: Int,
    previewAead: Int?,
) = addGroup(
    name = TextWrap.Resource(R.string.settings_encryption)
) {
    addItem(
        icon = Icons.Outlined.Lock,
        title = TextWrap.Resource(id = R.string.encryption_type),
        summary = if (fileAead == -1) {
            TextWrap.Resource(id = R.string.withoutEncryption)
        } else TextWrap.Text(text = KeysetTemplates.Stream.entries[fileAead].name)
    )
    addItem(
        icon = Icons.Outlined.Lock,
        title = TextWrap.Resource(id = R.string.thumb_encryption_type),
        summary = if (previewAead == null || previewAead == -1) {
            TextWrap.Resource(id = R.string.withoutEncryption)
        } else TextWrap.Text(text = KeysetTemplates.Stream.entries[previewAead].name)
    )
}