package com.chris.flightsearch.model

data class UiState(
    val airportQueryList: List<Airport>? = null,
    val airportSearchQuery: String = "",
    var selectedAirportPlaceholder: SelectedAirport = SelectedAirport("", "", 0),
    val favorite: Favorite? = null,

)

data class AirportList(
    val list: List<Airport>
)

data class SelectedAirport(
    val name: String,
    val iataCode: String,
    val passengers: Int
)

data class FavoriteList(
    val list: List<Favorite>
)




