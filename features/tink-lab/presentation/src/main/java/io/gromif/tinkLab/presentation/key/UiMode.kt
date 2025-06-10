package io.gromif.tinkLab.presentation.key

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal sealed class UiMode : Parcelable {

    @Parcelize
    data object CreateKey : UiMode()

    @Parcelize
    data class LoadKey(
        val keysetPath: String
    ) : UiMode()
}
