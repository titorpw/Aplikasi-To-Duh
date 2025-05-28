package com.l0123137.tesprojek.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Turquoise,
    onPrimary = Color.Black,
    primaryContainer = SteelBlue,
    onPrimaryContainer = Color.White,
    secondary = TealBlue,
    onSecondary = Color.White,
    tertiary = md_theme_light_tertiary,
    background = DarkCyan,
    onBackground = Color.Black,
    surface = DarkCyan,
    onSurface = Color.White,
    error = CoralRed,
    onError = Color.Black,
)

private val LightColorScheme = lightColorScheme(
    primary = Turquoise,
    onPrimary = Color.White,
    primaryContainer = SteelBlue,
    secondaryContainer = RoyalBlue,
    onPrimaryContainer = Color.White,
    secondary = TealBlue,
    onSecondary = Color.White,
    tertiary = md_theme_dark_tertiary,
    background = Color.White,
    onBackground = DarkCyan,
    surface = Color.White,
    onSurface = Color.Black,
    error = CoralRed,
    onError = Color.White,
)

@Composable
fun TesProjekTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}