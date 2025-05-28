package com.l0123137.tesprojek.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class UserPreferences(private val context: Context) {
    private val Context.dataStore by preferencesDataStore("user_prefs")

    companion object {
        private val USERNAME_KEY = stringPreferencesKey("username")
        private val PASSWORD_KEY = stringPreferencesKey("password")
    }

    val usernameFlow: Flow<String?> = context.dataStore.data
        .map { prefs -> prefs[USERNAME_KEY] }

    val passwordFlow: Flow<String?> = context.dataStore.data
        .map { prefs -> prefs[PASSWORD_KEY] }

    fun getUser(): Flow<UserData> {
        return usernameFlow.combine(passwordFlow) { username, password ->
            UserData(username, password)
        }
    }

    suspend fun saveUser(username: String, password: String) {
        context.dataStore.edit { prefs ->
            prefs[USERNAME_KEY] = username
            prefs[PASSWORD_KEY] = password
        }
    }
}

data class UserData(val username: String?, val password: String?)