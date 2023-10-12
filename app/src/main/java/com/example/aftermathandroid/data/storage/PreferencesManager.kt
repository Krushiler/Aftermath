package com.example.aftermathandroid.data.storage

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesManager @Inject constructor(private val preferences: SharedPreferences) {
    private val _tokenFlow = MutableStateFlow<String?>(null)
    val tokenFlow: StateFlow<String?> = _tokenFlow

    var token: String? = null
        set(value) {
            field = value
            _tokenFlow.value = value
            preferences.edit(commit = true) {
                putString(TOKEN_KEY, value)
            }
        }

    init {
        token = preferences.getString(TOKEN_KEY, null)
        _tokenFlow.value = token
    }

    companion object {
        private const val TOKEN_KEY = "TOKEN"
    }
}