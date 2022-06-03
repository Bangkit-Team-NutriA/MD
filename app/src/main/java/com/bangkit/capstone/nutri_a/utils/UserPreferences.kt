package com.bangkit.capstone.nutri_a.utils

import androidx.datastore.core.DataStore
import com.bangkit.capstone.nutri_a.model.Auth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    fun getUser() : Flow<Auth> {
        return dataStore.data.map {preferences ->
            Auth(
                preferences[TOKEN_KEY] ?:"",
                preferences[STATE_KEY] ?: false
            )
        }
    }

    suspend fun saveUser(auth: Auth) {
        dataStore.edit {preferences ->
            preferences[TOKEN_KEY] = auth.token
            preferences[STATE_KEY] = auth.isLogin
        }
    }

    suspend fun logout() {
        dataStore.edit {preferences ->
            preferences[STATE_KEY] = false
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val TOKEN_KEY = stringPreferencesKey("token")
        private val STATE_KEY = booleanPreferencesKey("status")

        fun getInstance(dataStore: DataStore<Preferences>) : UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}