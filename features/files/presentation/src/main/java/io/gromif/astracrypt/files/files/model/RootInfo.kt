package io.gromif.astracrypt.files.files.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RootInfo(
    val id: Long = 0,
    val name: String = "",
) : Parcelable