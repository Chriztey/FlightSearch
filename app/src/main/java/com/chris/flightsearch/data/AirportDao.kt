package com.chris.flightsearch.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.chris.flightsearch.model.Airport
import kotlinx.coroutines.flow.Flow

@Dao
interface AirportDao {
    @Query("SELECT * FROM airport")
    fun getAllAirports(): Flow<List<Airport>>

    @Query("SELECT * FROM airport WHERE iata_code = :iataCode")
    fun getAirportByIATA(iataCode: String): Flow<Airport?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAirport(airport: Airport)

    @Update
    suspend fun updateAirport(airport: Airport)

    @Delete
    suspend fun deleteAirport(airport: Airport)

    @Query("SELECT * FROM airport WHERE iata_code LIKE '%'||:query||'%' OR name LIKE '%'||:query||'%'")
    suspend fun searchAirports(query: String): List<Airport>

    @Query("SELECT * FROM airport WHERE NOT iata_code = :iataCode")
    fun getAirportDestination(iataCode: String): Flow<List<Airport>?>



}