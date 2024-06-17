package com.chris.flightsearch.data

import com.chris.flightsearch.model.Airport
import kotlinx.coroutines.flow.Flow

interface AirportRepository{
    fun getAirportByIATA(iata: String): Flow<Airport?>

    suspend fun getAirportByQuery(query: String): List<Airport>

    suspend fun updateAirport (airport: Airport)

    suspend fun deleteAirport (airport: Airport)

    suspend fun insertAirport (airport: Airport)

    fun getAirportDestination(iata: String): Flow<List<Airport>?>

    fun getAllAirport(): Flow<List<Airport>>
}