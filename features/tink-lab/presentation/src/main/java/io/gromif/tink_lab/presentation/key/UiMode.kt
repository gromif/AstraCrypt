package io.gromif.tink_lab.presentation.key

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