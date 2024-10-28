package com.qasim.zaka.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color.White,       // White for primary elements (e.g., buttons, text)
    onPrimary = Color.Black,      // Black for text on primary elements
    secondary = Color.Gray,       // Gray for secondary elements
    onSecondary = Color.White,    // White for text on secondary elements
    background = Color.DarkGray,     // Black background for true dark mode
    onBackground = Color.White,   // White text on the background
    surface = Color.DarkGray,     // Dark gray for surfaces like cards, input fields
    onSurface = Color.White       // White text on surfaces
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6200EE),
    onPrimary = Color.White,
    secondary = Color(0xFF03DAC6),
    onSecondary = Color.Black,
    background = Color.White,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black
)

@Composable
fun MyAppTheme(
    darkTheme: Boolean = true, // Default to true to ensure dark theme is applied
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}
