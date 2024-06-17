package com.chris.flightsearch.data

import com.chris.flightsearch.model.Favorite
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    suspend fun insertFavorite(favorite: Favorite)

    suspend fun deleteFavorite(favorite: Favorite)

    fun getAllFavorite(): Flow<List<Favorite>>

    suspend fun checkFavorite(departureCode: String, destinationCode: String): Favorite
}