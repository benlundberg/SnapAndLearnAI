package com.app.snaplearnai.shared.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalContext

private val darkColorPalette = darkColorScheme(
    primary = primary,
    secondary = secondary,
    background = darkBackground,
    error = brickRed,
    surface = darkBackground,
    onSurface = white,
    onBackground = white,
    surfaceContainer = gray600
)

private val lightColorPalette = lightColorScheme(
    primary = primary,
    secondary = secondary,
    background = lightBackground,
    error = brickRed,
    surface = lightBackground,
    onSurface = black,
    onBackground = black,
    surfaceContainer = gray100
)

object AppTheme {
    val spacings: Spacing
        @Composable
        @ReadOnlyComposable
        get() = LocalSpacing.current

    val color: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalColor.current

    val typography: Typography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current
}

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> darkColorPalette
        else -> lightColorPalette
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}