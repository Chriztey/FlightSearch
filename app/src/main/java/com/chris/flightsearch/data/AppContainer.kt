package com.chris.flightsearch.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.chris.flightsearch.datastore.UserPreferencesRepository

interface AppContainer{
    val airportRepository: AirportRepository
    val favoriteRepository: FavoriteRepository
    val userPreferencesRepository: UserPreferencesRepository
}

private const val SEARCH_QUERY_KEY = "search_query_key"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = SEARCH_QUERY_KEY
)
class DefaultAppContainer(private val context: Context): AppContainer {
    override val airportRepository: AirportRepository by lazy {
        val database = AirportDatabase.getDatabase(context)
        OfflineAirportRepository(database.airportDao()
        )
//        OfflineAirportRepository(AirportDatabase.getDatabase(context).airportDao())
    }

    override val favoriteRepository: FavoriteRepository by lazy {
        val database = AirportDatabase.getDatabase(context)
        OfflineFavoriteRepository(database.favoriteDao()
        )
    }

    override val userPreferencesRepository: UserPreferencesRepository by lazy {
        UserPreferencesRepository(context.dataStore)
    }


}