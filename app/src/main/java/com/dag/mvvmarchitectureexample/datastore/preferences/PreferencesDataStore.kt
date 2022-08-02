package com.dag.mvvmarchitectureexample.datastore.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map

class PreferencesDataStore(private val context: Context) {

    var loggedInScope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    private val USER_PREFERENCES_NAME = "preferencesStore"
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_PREFERENCES_NAME, scope = loggedInScope)

    suspend fun <T> read(key: Preferences.Key<T>) = context.dataStore.data.map { preferences ->
        preferences[key]
    }

    suspend fun <T> write(key: Preferences.Key<T>, value: T) {
        context.dataStore.edit { preferences ->
            preferences[key] = value
        }
    }
}