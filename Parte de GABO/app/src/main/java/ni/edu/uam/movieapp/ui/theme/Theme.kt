package ni.edu.uam.movieapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = LightBlue,
    secondary = SoftBlue,
    tertiary = FavoriteRed,
    background = DarkBlue,
    surface = BlueGray,
    onPrimary = Cream,
    onSecondary = Cream,
    onTertiary = Cream,
    onBackground = Cream,
    onSurface = Cream
)

private val LightColorScheme = lightColorScheme(
    primary = SoftBlue,
    secondary = LightBlue,
    tertiary = FavoriteRed,
    background = BackgroundLight,
    surface = CardBackground,
    onPrimary = Cream,
    onSecondary = DarkBlue,
    onTertiary = Cream,
    onBackground = DarkBlue,
    onSurface = DarkBlue
)

@Composable
fun MovieAppTheme(
    darkTheme: Boolean = false,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}