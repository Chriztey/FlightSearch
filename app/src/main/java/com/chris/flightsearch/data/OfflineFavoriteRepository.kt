package com.chris.flightsearch.data

import com.chris.flightsearch.model.Favorite
import kotlinx.coroutines.flow.Flow

class OfflineFavoriteRepository(private val favoriteDao: FavoriteDao): FavoriteRepository {

    override fun getAllFavorite(): Flow<List<Favorite>> {
        return favoriteDao.getAllFavorites()
    }

    override suspend fun insertFavorite(favorite: Favorite) {
        return favoriteDao.insertFavorite(favorite)
    }

    override suspend fun deleteFavorite(favorite: Favorite) {
        return favoriteDao.deleteFavorite(favorite)
    }

    override suspend fun checkFavorite(
        departureCode: String,
        destinationCode: String
    ): Favorite {
        return favoriteDao.checkFavorite(departureCode, destinationCode)
    }

}