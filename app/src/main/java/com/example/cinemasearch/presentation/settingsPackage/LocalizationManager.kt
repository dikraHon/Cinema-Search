@file:Suppress("DEPRECATION")

package com.example.cinemasearch.presentation.settingsPackage

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import java.util.*
import androidx.core.content.edit

object LocalizationManager {
    private const val PREFS_NAME = "lang_prefs"
    private const val LANG_KEY = "app_language"

    fun setAppLocale(context: Context?, language: String): Context {
        requireNotNull(context) { "Context cannot be null" }
        persistLanguage(context, language)
        return updateResources(context, language)
    }


    private fun persistLanguage(context: Context, language: String) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit { putString(LANG_KEY, language) }
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun updateResources(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val resources = context.resources
        val config = Configuration(resources.configuration)
        config.setLocale(locale)

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.createConfigurationContext(config)
        } else {
            resources.updateConfiguration(config, resources.displayMetrics)
            context
        }
    }

    fun getCurrentLanguage(context: Context): String {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString(LANG_KEY, Locale.getDefault().language) ?: "en"
    }
}