package io.gromif.ui.compose.core.wrappers

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.vector.ImageVector

class IconWrap {
    @DrawableRes
    var id: Int? = null
        private set
    var imageVector: ImageVector? = null
        private set

    constructor(@DrawableRes id: Int) {
        this.id = id
    }

    constructor(imageVector: ImageVector) {
        this.imageVector = imageVector
    }
}