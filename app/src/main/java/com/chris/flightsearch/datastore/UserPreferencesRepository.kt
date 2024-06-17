package com.chris.flightsearch.datastore

import android.content.ContentValues.TAG
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {
    private companion object {
        val SEARCH_QUERY_KEY = stringPreferencesKey("search_query_key")
    }

    suspend fun saveSearchQuery(searchQueryKey: String) {
        dataStore.edit { preferences ->
            preferences[SEARCH_QUERY_KEY] = searchQueryKey
        }
    }

    val savedSearchQuery: Flow<String> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map { preferences ->
            preferences[SEARCH_QUERY_KEY] ?: ""
        }

}