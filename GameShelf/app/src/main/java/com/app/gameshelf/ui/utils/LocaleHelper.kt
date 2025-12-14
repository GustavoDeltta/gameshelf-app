package com.app.gameshelf.utils

import android.content.Context
import android.content.res.Configuration
import java.util.*
object LocaleHelper {

    fun setLocale(context: Context, languageCode: String): Context {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)

        return context.createConfigurationContext(config)
    }

    fun applyLocale(context: Context): Context {
        val prefsManager = PreferencesManager(context)
        val languageCode = prefsManager.getLanguage()
        return setLocale(context, languageCode)
    }
}