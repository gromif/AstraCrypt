package io.gromif.compose.details.model

import io.gromif.ui.compose.core.wrappers.TextWrap

data class DetailsGroup(
    val title: TextWrap,
    val detailsItemList: List<DetailsItem>,
)