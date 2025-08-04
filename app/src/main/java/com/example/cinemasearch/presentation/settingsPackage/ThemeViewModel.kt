package com.example.cinemasearch.presentation.settingsPackage

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import jakarta.inject.Inject

class ThemeViewModel @Inject constructor(
    private val context: Context
) : ViewModel() {
    var isDarkTheme by mutableStateOf(ThemeManager.isDarkTheme(context))
        private set

    fun toggleTheme() {
        isDarkTheme = !isDarkTheme
        ThemeManager.setDarkTheme(context, isDarkTheme)
    }
}