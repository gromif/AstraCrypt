package io.gromif.astracrypt.files.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class Mode: Parcelable {

    @Parcelize
    data object Default: Mode()

    @Parcelize
    data class Multiselect(val count: Int): Mode()

}