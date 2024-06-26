package com.ideaapp.ui.screens.settings


import android.app.Application
import androidx.lifecycle.ViewModel
import com.ideaapp.utils.ThemeChanger
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val application: Application,
) : ViewModel() {

    fun themeChange() {
        ThemeChanger.saveThemeMode(
            context = application.applicationContext,
            isDarkMode = !(ThemeChanger.isDarkMode.value ?: false),
        )
    }

    fun dynamicThemeChange() {
        ThemeChanger.saveDynamicTheme(
            context = application.applicationContext,
            isDynamicTheme = !(ThemeChanger.isDynamicTheme.value ?: false)
        )
    }
}
