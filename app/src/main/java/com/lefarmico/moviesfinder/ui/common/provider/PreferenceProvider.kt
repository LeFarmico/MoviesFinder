package com.lefarmico.moviesfinder.ui.common.provider

import android.content.Context
import androidx.core.content.edit

class PreferenceProvider(context: Context) {

    private val appContext = context.applicationContext

    private val preferences = appContext.getSharedPreferences("settings", Context.MODE_PRIVATE)

    init {
        if (preferences.getBoolean(KEY_FIRST_LAUNCH, false)) {
            preferences.edit { putString(KEY_COUNTRY, DEFAULT_COUNTRY) }
            preferences.edit { putBoolean(KEY_FIRST_LAUNCH, false) }
        }
    }

    fun setDefaultCountry(country: String) {
        preferences.edit { putString(KEY_COUNTRY, country) }
    }

    fun getCurrentCountry(): String {
        return preferences.getString(KEY_COUNTRY, DEFAULT_COUNTRY) ?: DEFAULT_COUNTRY
    }
    companion object {
        private const val KEY_FIRST_LAUNCH = "first_launch"
        private const val KEY_COUNTRY = "country"
        private const val DEFAULT_COUNTRY = "US"
    }
}
