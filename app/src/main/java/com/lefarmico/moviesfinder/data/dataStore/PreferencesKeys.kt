package com.lefarmico.moviesfinder.data.dataStore

import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKeys {
    val USERS_LANGUAGE = stringPreferencesKey("users_language")
    val USERS_REGION = stringPreferencesKey("users_region")
}
