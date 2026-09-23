package com.example.ui.theme

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
    primary = FloOliveSoft,
    onPrimary = FloOliveDark,
    primaryContainer = FloOliveDark,
    onPrimaryContainer = Color.White,
    secondary = FloAmberGold,
    onSecondary = Color.Black,
    background = Color(0xFF161D10),
    surface = Color(0xFF1E2816),
    onBackground = Color(0xFFF2F6EA),
    onSurface = Color(0xFFF2F6EA),
    surfaceVariant = Color(0xFF2B3820),
    onSurfaceVariant = Color(0xFFA5B892),
    outline = Color(0xFF405330)
)

private val LightColorScheme = lightColorScheme(
    primary = FloOliveDark,
    onPrimary = Color.White,
    primaryContainer = FloBgPistachio,
    onPrimaryContainer = FloOliveDark,
    secondary = FloOliveLight,
    onSecondary = Color.White,
    background = FloBgCream,
    surface = FloCardBg,
    onBackground = FloTextDark,
    onSurface = FloTextDark,
    surfaceVariant = FloBgSage,
    onSurfaceVariant = FloTextMuted,
    outline = FloCardBorder
)

@Composable
fun FloTheme(
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

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    FloTheme(darkTheme = darkTheme, dynamicColor = dynamicColor, content = content)
}
