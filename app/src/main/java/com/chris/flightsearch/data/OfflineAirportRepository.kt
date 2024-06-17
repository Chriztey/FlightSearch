package com.chris.flightsearch.data

import com.chris.flightsearch.model.Airport
import kotlinx.coroutines.flow.Flow

class OfflineAirportRepository (private val airportDao: AirportDao): AirportRepository {
    override fun getAirportByIATA(iata: String): Flow<Airport?> {
        return airportDao.getAirportByIATA(iata)
    }

    override suspend fun getAirportByQuery(query: String): List<Airport> {
        return airportDao.searchAirports(query)
    }

    override suspend fun insertAirport(airport: Airport) {
        return airportDao.insertAirport(airport)
    }

    override suspend fun updateAirport(airport: Airport) {
        return airportDao.updateAirport(airport)
    }

    override suspend fun deleteAirport(airport: Airport) {
        return airportDao.deleteAirport(airport)
    }

    override fun getAirportDestination(iata: String): Flow<List<Airport>?> {
        return airportDao.getAirportDestination(iata)
    }

    override fun getAllAirport(): Flow<List<Airport>> {
        return airportDao.getAllAirports()
    }
}