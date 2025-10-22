package com.app.snaplearnai.shared.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf

val Color.light: Color
    get() = this.copy(alpha = 12f)

fun Color.lighter(value: Float) =
    this.copy(alpha = value)

val primary = Color(0xFF4CAF50)
val secondary = Color(0xFF42A3D2)
val white = Color(0xFFFFFFFF)
val black = Color(0xFF2B2B2B)
val brickRed = Color(0xFFEE4285)
val sunYellow = Color(0xFFFFAA47)
val grassGreen = Color(0xFF9ABB70)
val skyBlue = Color(0xFF2BC6EB)

val gray50 = Color(0xFFF4F5F7)
val gray100 = Color(0xFFE1E1E1)
val gray200 = Color(0xFFC8C8C8)
val gray300 = Color(0xFFACACAC)
val gray400 = Color(0xFF919191)
val gray500 = Color(0xFF6E6E6E)
val gray600 = Color(0xFF404040)
val gray900 = Color(0xFF212121)
val gray950 = Color(0xFF141414)

val lightBackground = Color(0xFFF9F9FB)
val darkBackground = Color(0xFF272746)

val topBar = Color(0xFF2B2930)
val topBarContent = Color(0xFFF9F9FB)

val colors = Color(
    lightBackground = lightBackground,
    darkBackground = darkBackground,
    primary = primary,
    secondary = secondary,
    white = white,
    black = black,
    brickRed = brickRed,
    sunYellow = sunYellow,
    grassGreen = grassGreen,
    skyBlue = skyBlue,
    topBar = topBar,
    topBarContent = topBarContent,
    gray50 = gray50,
    gray100 = gray100,
    gray200 = gray200,
    gray300 = gray300,
    gray400 = gray400,
    gray500 = gray500,
    gray600 = gray600,
    gray900 = gray900,
    gray950 = gray950
)

@Immutable
data class Color(
    val lightBackground: Color,
    val darkBackground: Color,
    val primary: Color,
    val secondary: Color,
    val white: Color,
    val black: Color,
    val brickRed: Color,
    val sunYellow: Color,
    val grassGreen: Color,
    val skyBlue: Color,
    val topBar: Color,
    val topBarContent: Color,
    val gray50: Color,
    val gray100: Color,
    val gray200: Color,
    val gray300: Color,
    val gray400: Color,
    val gray500: Color,
    val gray600: Color,
    val gray900: Color,
    val gray950: Color
)

val LocalColor = staticCompositionLocalOf { colors }