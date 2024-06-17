package com.example.travellabel.Data.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class UserPreference private constructor(private val dataStore : DataStore<Preferences>) {

    suspend fun saveSession(user: UserModel) {
        dataStore.edit { preferences ->
            preferences[STATUS_KEY] = user.status
            preferences[MESSAGE_KEY] = user.message
            preferences[ACCESS_KEY] = user.accessToken
            preferences[REFRESH_KEY] = user.refreshToken
            preferences[IS_LOGIN_KEY] = true
        }
    }

    fun getSession(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            UserModel(
                preferences[STATUS_KEY].toString(),
                preferences[MESSAGE_KEY].toString(),
                preferences[ACCESS_KEY].toString(),
                preferences[REFRESH_KEY].toString(),
                preferences[IS_LOGIN_KEY] ?: false
            )
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val STATUS_KEY = stringPreferencesKey("status")
        private val MESSAGE_KEY = stringPreferencesKey("message")
        private val ACCESS_KEY = stringPreferencesKey("accessToken")
        private val REFRESH_KEY = stringPreferencesKey("refreshToken")
        private val IS_LOGIN_KEY = booleanPreferencesKey("isLogin")

        fun getInstance(dataStore: DataStore<Preferences>) :UserPreference {
            return INSTANCE ?: synchronized(this){
                val instance = UserPreference(dataStore)
                INSTANCE = instance

                instance
            }
        }
    }
}