package com.app.gameshelf.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class PreferencesManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    private val authPrefs = EncryptedSharedPreferences.create(
        "auth_prefs",
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    companion object {
        private const val PREFS_NAME = "gameshelf_preferences"
        private const val KEY_DARK_THEME = "dark_theme"
        private const val KEY_LANGUAGE = "language"
    }

    // Theme ----------------------------

    fun saveDarkTheme(isDark: Boolean) {
        prefs.edit().putBoolean(KEY_DARK_THEME, isDark).apply()
    }

    fun isDarkTheme(): Boolean {
        return prefs.getBoolean(KEY_DARK_THEME, true)
    }

    // Language -------------------------

    fun saveLanguage(languageCode: String) {
        prefs.edit().putString(KEY_LANGUAGE, languageCode).apply()
    }

    fun getLanguage(): String {
        return prefs.getString(KEY_LANGUAGE, null) ?: getSystemLanguageOrDefault()
    }

    private fun getSystemLanguageOrDefault(): String {
        val systemLanguage = java.util.Locale.getDefault().language
        return if (systemLanguage == "pt") "pt" else "en"
    }

    fun getApiLanguage(): String {
        val languageCode = getLanguage()
        return when (languageCode) {
            "pt" -> "brazilian"
            "en" -> "english"
            else -> "english"
        }
    }

    // Auth Token -----------------------

    fun getAuthToken(): String? {
        return authPrefs.getString("jwt_token", null)
    }
}
