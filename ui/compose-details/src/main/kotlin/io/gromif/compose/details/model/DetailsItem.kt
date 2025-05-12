package io.gromif.compose.details.model

import androidx.compose.ui.graphics.vector.ImageVector
import io.gromif.ui.compose.core.wrappers.TextWrap

data class DetailsItem(
    val icon: ImageVector,
    val title: TextWrap,
    val summary: TextWrap
)