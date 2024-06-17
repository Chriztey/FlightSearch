package com.chris.flightsearch.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.chris.flightsearch.FlightSearchApp

object AppViewModelProvider {

    val Factory = viewModelFactory {
        initializer {
            AirportViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                flightSearchApplication().container.airportRepository,
                flightSearchApplication().container.favoriteRepository,
                flightSearchApplication().container.userPreferencesRepository
            )
        }

    }


}
fun CreationExtras.flightSearchApplication(): FlightSearchApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FlightSearchApp)