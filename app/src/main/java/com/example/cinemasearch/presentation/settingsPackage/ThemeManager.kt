package com.example.cinemasearch.presentation.settingsPackage

import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.core.content.edit

object ThemeManager {
    private const val PREFS_NAME = "app_settings"
    private const val THEME_KEY = "dark_theme"

    fun setDarkTheme(context: Context, enabled: Boolean) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit { putBoolean(THEME_KEY, enabled) }
    }

    fun isDarkTheme(context: Context): Boolean {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getBoolean(THEME_KEY, false)
    }
}