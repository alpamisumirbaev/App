package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = AcademicTealDark,
    secondary = SecondaryAmberDark,
    tertiary = AcademicTealDark,
    background = CoolSlate900,
    surface = CoolSlate800,
    onPrimary = CoolSlate900,
    onSecondary = CoolSlate900,
    onBackground = CoolSlate100,
    onSurface = CoolSlate100,
    primaryContainer = AcademicTealDark.copy(alpha = 0.2f),
    surfaceVariant = CoolSlate800.copy(alpha = 0.8f),
    onSurfaceVariant = CoolSlate100.copy(alpha = 0.7f)
)

private val LightColorScheme = lightColorScheme(
    primary = AcademicTealLight,
    secondary = SecondaryAmberLight,
    tertiary = AcademicTealLight,
    background = WarmLightBackground,
    surface = LightSurface,
    onPrimary = LightSurface,
    onSecondary = LightSurface,
    onBackground = HeavyDarkGrey,
    onSurface = HeavyDarkGrey,
    primaryContainer = AcademicTealLight.copy(alpha = 0.15f),
    surfaceVariant = CoolSlate100,
    onSurfaceVariant = HeavyDarkGrey.copy(alpha = 0.7f)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color support disabled to preserve our custom educational branding
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
