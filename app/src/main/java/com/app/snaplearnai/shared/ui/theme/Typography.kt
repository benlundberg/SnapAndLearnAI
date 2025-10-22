package com.app.snaplearnai.shared.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.app.snaplearnai.R

private val appFont = FontFamily(
    Font(resId = R.font.lexend_regular, weight = FontWeight.Normal),
    Font(resId = R.font.lexend_semi_bold, weight = FontWeight.Medium)
)

val TextStyle.primaryColor: TextStyle
    get() = this.copy(color = primary)

val TextStyle.secondaryColor: TextStyle
    get() = this.copy(color = secondary)

val TextStyle.whiteColor: TextStyle
    get() = this.copy(color = white)

val TextStyle.blackColor: TextStyle
    get() = this.copy(color = black)

val TextStyle.sunYellowColor: TextStyle
    get() = this.copy(color = sunYellow)

val TextStyle.semiBold: TextStyle
    get() = this.copy(fontWeight = FontWeight.SemiBold)

data class Typography(
    val micro: TextStyle = TextStyle(
        fontFamily = appFont,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp
    ),
    val small: TextStyle = TextStyle(
        fontFamily = appFont,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    val default: TextStyle = TextStyle(
        fontFamily = appFont,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    val medium: TextStyle = TextStyle(
        fontFamily = appFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    val large: TextStyle = TextStyle(
        fontFamily = appFont,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp
    ),
    val header: TextStyle = TextStyle(
        fontFamily = appFont,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),
    val defaultItalic: TextStyle = TextStyle(
        fontFamily = appFont,
        fontWeight = FontWeight.Normal,
        fontStyle = FontStyle.Italic,
        fontSize = 14.sp
    )
)

val LocalTypography = staticCompositionLocalOf { Typography() }