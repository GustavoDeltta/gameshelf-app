package com.app.gameshelf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import com.app.gameshelf.ui.App
import com.app.gameshelf.ui.theme.GameShelfTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Global state for theme
            var isDarkTheme by remember { mutableStateOf(true) }

            AppThemeProvider(
                isDarkTheme = isDarkTheme,
                onThemeChange = { isDark -> isDarkTheme = isDark }
            ) {
                GameShelfTheme(darkTheme = isDarkTheme) {
                    App()
                }
            }
        }
    }
}

object ThemeState {
    var isDarkTheme by mutableStateOf(true)
    var onThemeChange: (Boolean) -> Unit = {}
}

@Composable
fun AppThemeProvider(
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit,
    content: @Composable () -> Unit
) {
    ThemeState.isDarkTheme = isDarkTheme
    ThemeState.onThemeChange = onThemeChange
    content()
}