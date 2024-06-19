package com.chris.flightsearch.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chris.flightsearch.model.AirportList
import com.chris.flightsearch.model.Favorite
import com.chris.flightsearch.model.FavoriteList
import com.chris.flightsearch.model.SelectedAirport
import com.chris.flightsearch.screen.FlightDepartDestinationCard
import com.chris.flightsearch.viewmodel.AirportViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun RouteList(
    destinationList: AirportList,
    favoriteList: FavoriteList,
    selectedAirport: SelectedAirport,
    scope: CoroutineScope,
    viewModel: AirportViewModel
    ) {

    LazyColumn(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            destinationList.list,
            key = { it.iataCode }
        ) {route ->
            val isFavorite = favoriteList.list.any {
                it.departureCode == selectedAirport.iataCode &&
                        it.destinationCode == route.iataCode
            }
            val unFav = favoriteList.list.find {
                it.departureCode == selectedAirport.iataCode &&
                        it.destinationCode == route.iataCode
            }

            FlightDepartDestinationCard(
                chosenAirport = selectedAirport.iataCode,
                chosenAirportName = selectedAirport.name,
                destinationList = route.iataCode,
                destinationName = route.name,
                addFavorite = {
                    scope.launch {
                        viewModel.addFavorite(
                            Favorite(
                                departureCode = selectedAirport.iataCode,
                                destinationCode = route.iataCode
                            )
                        )
                    }
                },
                removeFavorite = {
                    scope.launch {
                        if (unFav != null) {
                            viewModel.removeFavorite(unFav)
                        }
                    }
                },
                isFavorite = isFavorite
            )
        }
    }

}