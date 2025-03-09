package io.gromif.astracrypt.ui.design_system

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val LocalDarkCustomColors = staticCompositionLocalOf { extendedDark }
val LocalLightCustomColors = staticCompositionLocalOf { extendedLight }

val MaterialTheme.extendedColorScheme
    @Composable get() =
        (if (isSystemInDarkTheme()) LocalDarkCustomColors else LocalLightCustomColors).current

@Immutable
data class ExtendedColorScheme(
    val blue: ColorFamily,
    val green: ColorFamily,
    val brown: ColorFamily,
    val violet: ColorFamily,
    val orange: ColorFamily,
    val lime: ColorFamily,
    val tela: ColorFamily,
)

internal val extendedLight = ExtendedColorScheme(
    blue = ColorFamily(
        blueLight,
        onBlueLight,
        blueContainerLight,
        onBlueContainerLight,
    ),
    green = ColorFamily(
        greenLight,
        onGreenLight,
        greenContainerLight,
        onGreenContainerLight,
    ),
    brown = ColorFamily(
        brownLight,
        onBrownLight,
        brownContainerLight,
        onBrownContainerLight,
    ),
    violet = ColorFamily(
        violetLight,
        onVioletLight,
        violetContainerLight,
        onVioletContainerLight,
    ),
    orange = ColorFamily(
        orangeLight,
        onOrangeLight,
        orangeContainerLight,
        onOrangeContainerLight,
    ),
    lime = ColorFamily(
        limeLight,
        onLimeLight,
        limeContainerLight,
        onLimeContainerLight,
    ),
    tela = ColorFamily(
        telaLight,
        onTelaLight,
        telaContainerLight,
        onTelaContainerLight,
    ),
)

internal val extendedDark = ExtendedColorScheme(
    blue = ColorFamily(
        blueDark,
        onBlueDark,
        blueContainerDark,
        onBlueContainerDark,
    ),
    green = ColorFamily(
        greenDark,
        onGreenDark,
        greenContainerDark,
        onGreenContainerDark,
    ),
    brown = ColorFamily(
        brownDark,
        onBrownDark,
        brownContainerDark,
        onBrownContainerDark,
    ),
    violet = ColorFamily(
        violetDark,
        onVioletDark,
        violetContainerDark,
        onVioletContainerDark,
    ),
    orange = ColorFamily(
        orangeDark,
        onOrangeDark,
        orangeContainerDark,
        onOrangeContainerDark,
    ),
    lime = ColorFamily(
        limeDark,
        onLimeDark,
        limeContainerDark,
        onLimeContainerDark,
    ),
    tela = ColorFamily(
        telaDark,
        onTelaDark,
        telaContainerDark,
        onTelaContainerDark,
    ),
)

internal val extendedLightMediumContrast = ExtendedColorScheme(
    blue = ColorFamily(
        blueLightMediumContrast,
        onBlueLightMediumContrast,
        blueContainerLightMediumContrast,
        onBlueContainerLightMediumContrast,
    ),
    green = ColorFamily(
        greenLightMediumContrast,
        onGreenLightMediumContrast,
        greenContainerLightMediumContrast,
        onGreenContainerLightMediumContrast,
    ),
    brown = ColorFamily(
        brownLightMediumContrast,
        onBrownLightMediumContrast,
        brownContainerLightMediumContrast,
        onBrownContainerLightMediumContrast,
    ),
    violet = ColorFamily(
        violetLightMediumContrast,
        onVioletLightMediumContrast,
        violetContainerLightMediumContrast,
        onVioletContainerLightMediumContrast,
    ),
    orange = ColorFamily(
        orangeLightMediumContrast,
        onOrangeLightMediumContrast,
        orangeContainerLightMediumContrast,
        onOrangeContainerLightMediumContrast,
    ),
    lime = ColorFamily(
        limeLightMediumContrast,
        onLimeLightMediumContrast,
        limeContainerLightMediumContrast,
        onLimeContainerLightMediumContrast,
    ),
    tela = ColorFamily(
        telaLightMediumContrast,
        onTelaLightMediumContrast,
        telaContainerLightMediumContrast,
        onTelaContainerLightMediumContrast,
    ),
)

internal val extendedLightHighContrast = ExtendedColorScheme(
    blue = ColorFamily(
        blueLightHighContrast,
        onBlueLightHighContrast,
        blueContainerLightHighContrast,
        onBlueContainerLightHighContrast,
    ),
    green = ColorFamily(
        greenLightHighContrast,
        onGreenLightHighContrast,
        greenContainerLightHighContrast,
        onGreenContainerLightHighContrast,
    ),
    brown = ColorFamily(
        brownLightHighContrast,
        onBrownLightHighContrast,
        brownContainerLightHighContrast,
        onBrownContainerLightHighContrast,
    ),
    violet = ColorFamily(
        violetLightHighContrast,
        onVioletLightHighContrast,
        violetContainerLightHighContrast,
        onVioletContainerLightHighContrast,
    ),
    orange = ColorFamily(
        orangeLightHighContrast,
        onOrangeLightHighContrast,
        orangeContainerLightHighContrast,
        onOrangeContainerLightHighContrast,
    ),
    lime = ColorFamily(
        limeLightHighContrast,
        onLimeLightHighContrast,
        limeContainerLightHighContrast,
        onLimeContainerLightHighContrast,
    ),
    tela = ColorFamily(
        telaLightHighContrast,
        onTelaLightHighContrast,
        telaContainerLightHighContrast,
        onTelaContainerLightHighContrast,
    ),
)

internal val extendedDarkMediumContrast = ExtendedColorScheme(
    blue = ColorFamily(
        blueDarkMediumContrast,
        onBlueDarkMediumContrast,
        blueContainerDarkMediumContrast,
        onBlueContainerDarkMediumContrast,
    ),
    green = ColorFamily(
        greenDarkMediumContrast,
        onGreenDarkMediumContrast,
        greenContainerDarkMediumContrast,
        onGreenContainerDarkMediumContrast,
    ),
    brown = ColorFamily(
        brownDarkMediumContrast,
        onBrownDarkMediumContrast,
        brownContainerDarkMediumContrast,
        onBrownContainerDarkMediumContrast,
    ),
    violet = ColorFamily(
        violetDarkMediumContrast,
        onVioletDarkMediumContrast,
        violetContainerDarkMediumContrast,
        onVioletContainerDarkMediumContrast,
    ),
    orange = ColorFamily(
        orangeDarkMediumContrast,
        onOrangeDarkMediumContrast,
        orangeContainerDarkMediumContrast,
        onOrangeContainerDarkMediumContrast,
    ),
    lime = ColorFamily(
        limeDarkMediumContrast,
        onLimeDarkMediumContrast,
        limeContainerDarkMediumContrast,
        onLimeContainerDarkMediumContrast,
    ),
    tela = ColorFamily(
        telaDarkMediumContrast,
        onTelaDarkMediumContrast,
        telaContainerDarkMediumContrast,
        onTelaContainerDarkMediumContrast,
    ),
)

internal val extendedDarkHighContrast = ExtendedColorScheme(
    blue = ColorFamily(
        blueDarkHighContrast,
        onBlueDarkHighContrast,
        blueContainerDarkHighContrast,
        onBlueContainerDarkHighContrast,
    ),
    green = ColorFamily(
        greenDarkHighContrast,
        onGreenDarkHighContrast,
        greenContainerDarkHighContrast,
        onGreenContainerDarkHighContrast,
    ),
    brown = ColorFamily(
        brownDarkHighContrast,
        onBrownDarkHighContrast,
        brownContainerDarkHighContrast,
        onBrownContainerDarkHighContrast,
    ),
    violet = ColorFamily(
        violetDarkHighContrast,
        onVioletDarkHighContrast,
        violetContainerDarkHighContrast,
        onVioletContainerDarkHighContrast,
    ),
    orange = ColorFamily(
        orangeDarkHighContrast,
        onOrangeDarkHighContrast,
        orangeContainerDarkHighContrast,
        onOrangeContainerDarkHighContrast,
    ),
    lime = ColorFamily(
        limeDarkHighContrast,
        onLimeDarkHighContrast,
        limeContainerDarkHighContrast,
        onLimeContainerDarkHighContrast,
    ),
    tela = ColorFamily(
        telaDarkHighContrast,
        onTelaDarkHighContrast,
        telaContainerDarkHighContrast,
        onTelaContainerDarkHighContrast,
    ),
)

@Immutable
data class ColorFamily(
    val color: Color,
    val onColor: Color,
    val colorContainer: Color,
    val onColorContainer: Color
)

internal val unspecified_scheme = ColorFamily(
    Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified
)