package com.chris.flightsearch.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chris.flightsearch.model.Airport
import com.chris.flightsearch.data.AirportRepository
import com.chris.flightsearch.data.FavoriteRepository
import com.chris.flightsearch.datastore.UserPreferencesRepository
import com.chris.flightsearch.model.AirportList
import com.chris.flightsearch.model.Favorite
import com.chris.flightsearch.model.FavoriteList
import com.chris.flightsearch.model.SelectedAirport
import com.chris.flightsearch.model.UiState
import com.chris.flightsearch.screen.QueryResultDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AirportViewModel(
    savedStateHandle: SavedStateHandle,
    private val airportRepository: AirportRepository,
    private val favoriteRepository: FavoriteRepository,
    private val userPreferencesRepository: UserPreferencesRepository
): ViewModel() {

    // Navigation
    private val iataCode: String =
        savedStateHandle[QueryResultDestination.queryArgument] ?: " "

    // UI State (Temporary/Placeholder)
    private val _uiState = MutableStateFlow(UiState())
    var uiState: StateFlow<UiState> = _uiState.asStateFlow()




    // Search Airport with Suggestion List
    fun updateQuery(newQuery: String) {
        _uiState.update {
            currentState -> currentState.copy(
            airportSearchQuery = newQuery
            )
        }
        updateSearchQueryKey(newQuery)
    }

    fun searchAirport(key: String = "") {
        viewModelScope.launch {
            val result = airportRepository.getAirportByQuery(key)

            _uiState.update {
                currentState -> currentState.copy(
                airportQueryList = result
                )
            }
        }
    }

    // Set Selected Airport By IATA CODE
    val selectedAirport: StateFlow<SelectedAirport> = airportRepository
        .getAirportByIATA(iataCode).map {
            if (it != null) {
                it.toSelectedAirport()
            } else {
                UiState().selectedAirportPlaceholder
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState().selectedAirportPlaceholder
        )

    // Get Airport Destination from Selected IATA CODE
    val destinationAirportList: StateFlow<AirportList> = airportRepository
        .getAirportDestination(iataCode).map {
            if (it != null) {
                AirportList(it)
            } else {
                AirportList(emptyList())
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AirportList(emptyList())
        )


    fun addFavorite(favorite: Favorite) {
        viewModelScope.launch {
            favoriteRepository.insertFavorite(favorite)
        }

    }

    fun removeFavorite(favorite: Favorite) {
        viewModelScope.launch {
            favoriteRepository.deleteFavorite(favorite)
        }

    }


    val favoriteRouteList: StateFlow<FavoriteList> = favoriteRepository
        .getAllFavorite().map {
            if (it != null) {
                FavoriteList(it)
            } else {
                FavoriteList(emptyList())
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = FavoriteList(emptyList())
        )

    init {
        viewModelScope.launch {
            userPreferencesRepository.savedSearchQuery.collect {
                updateQuery(it)
            }
        }
    }

    fun updateSearchQueryKey(key: String) {
        viewModelScope.launch {
            userPreferencesRepository.saveSearchQuery(key)
        }
    }

}



// Convert Data Class

fun Airport.toSelectedAirport(): SelectedAirport {
    return SelectedAirport (
        name = name,
        iataCode = iataCode,
        passengers = passengers
    )
}






