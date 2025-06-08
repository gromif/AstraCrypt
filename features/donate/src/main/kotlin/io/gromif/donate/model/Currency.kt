package io.gromif.donate.model

import androidx.annotation.DrawableRes
import io.gromif.donate.R

internal data class Currency(
    val name: String = "Bitcoin",
    val address: String = "#fiysifuyhsdiufiysdufsdyfusdyfyusidysdfj",
    @DrawableRes val iconResId: Int = R.drawable.currency_btc,
)
