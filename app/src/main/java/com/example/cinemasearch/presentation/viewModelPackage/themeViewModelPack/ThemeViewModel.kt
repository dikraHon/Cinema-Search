package com.example.cinemasearch.presentation.viewModelPackage.themeViewModelPack

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.cinemasearch.presentation.settingsPackage.ThemeManager
import jakarta.inject.Inject

class ThemeViewModel @Inject constructor(
    private val context: Application
) : ViewModel() {

    var isDarkTheme by mutableStateOf(ThemeManager.isDarkTheme(context))
        private set

    fun toggleTheme() {
        isDarkTheme = !isDarkTheme
        ThemeManager.setDarkTheme(context, isDarkTheme)
    }
}