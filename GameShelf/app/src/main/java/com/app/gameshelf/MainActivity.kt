package com.app.gameshelf

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import com.app.gameshelf.ui.App
import com.app.gameshelf.ui.theme.GameShelfTheme
import com.app.gameshelf.utils.LocaleHelper
import com.app.gameshelf.utils.PreferencesManager

class MainActivity : ComponentActivity() {

    private lateinit var prefsManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefsManager = PreferencesManager(this)

        enableEdgeToEdge()
        setContent {
            var isDarkTheme by remember { mutableStateOf(prefsManager.isDarkTheme()) }

            AppThemeProvider(
                isDarkTheme = isDarkTheme,
                onThemeChange = { isDark ->
                    isDarkTheme = isDark
                    prefsManager.saveDarkTheme(isDark)
                }
            ) {
                GameShelfTheme(darkTheme = isDarkTheme) {
                    App()
                }
            }
        }
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LocaleHelper.applyLocale(newBase))
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