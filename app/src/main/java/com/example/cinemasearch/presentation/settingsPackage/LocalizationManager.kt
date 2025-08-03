@file:Suppress("DEPRECATION")

package com.example.cinemasearch.presentation.settingsPackage

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import java.util.*
import androidx.core.content.edit

object LocalizationManager {
    private const val PREFS_NAME = "app_settings"
    private const val LANGUAGE_KEY = "language"

    fun setAppLocale(context: Context?, language: String): Context {
        require(context != null) { "Context cannot be null" }

        val validLanguages = setOf("en", "ru")
        require(language in validLanguages) { "Unsupported language: $language" }

        persistLanguage(context, language)
        return updateResources(context, language)
    }

    fun getCurrentLanguage(context: Context?): String {
        val prefs = context?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs?.getString(LANGUAGE_KEY, Locale.getDefault().language) ?: "en"
    }

    private fun persistLanguage(context: Context, language: String) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit {
                putString(LANGUAGE_KEY, language)
            }
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun updateResources(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val resources = context.resources
        val configuration = Configuration(resources.configuration)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.setLocale(locale)
            configuration.setLayoutDirection(locale)
            return context.createConfigurationContext(configuration)
        } else {
            configuration.locale = locale
            configuration.setLayoutDirection(locale)
            resources.updateConfiguration(configuration, resources.displayMetrics)
        }

        return context
    }

    fun applyLanguage(context: Context) {
        val language = getCurrentLanguage(context)
        updateResources(context, language)
    }
}