package com.chris.flightsearch.screen


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chris.flightsearch.component.RouteList
import com.chris.flightsearch.component.SearchBar
import com.chris.flightsearch.navigation.Nav
import com.chris.flightsearch.viewmodel.AirportViewModel
import com.chris.flightsearch.viewmodel.AppViewModelProvider

object QueryResultDestination: Nav{
    override var routeName: String = "Query Result"

    override val routeDestination: String = "query_result"

    const val queryArgument = "iata"
    val argRoute = "$routeDestination/{$queryArgument}"
}

@Composable
fun QueryResultScreen(
    viewModel: AirportViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onNavToQuery: (String) -> Unit,
) {
    val queryResultUiState by viewModel.uiState.collectAsState()
    val destinationList by viewModel.destinationAirportList.collectAsState()
    val selectedAirport by viewModel.selectedAirport.collectAsState()
    val favoriteList by viewModel.favoriteRouteList.collectAsState()
    val scope = rememberCoroutineScope()
    var focus by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
        SearchBar(
            viewModel = viewModel,
            airportSearchQuery = queryResultUiState.airportSearchQuery,
            onFocusChange = {focus = it},
            focus = focus,
            airportQueryList = queryResultUiState.airportQueryList,
            onNavToQuery = {onNavToQuery(it)},
            scope = scope,
        )
        Text(
            text = "Flights From ${QueryResultDestination.routeName}",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp))
        RouteList(
            destinationList = destinationList,
            favoriteList = favoriteList,
            selectedAirport = selectedAirport,
            scope = scope,
            viewModel = viewModel
        )
    }

}

@Composable
fun FlightDepartDestinationCard(
    modifier: Modifier = Modifier,
    chosenAirport: String,
    chosenAirportName: String,
    destinationList: String,
    destinationName: String,
    addFavorite: () -> Unit = {},
    removeFavorite: () -> Unit = {},
    isFavorite: Boolean = false
){
    Card(
        modifier = Modifier
            .heightIn(
                min = 125.dp,
            )
            .fillMaxWidth(),
        shape = RoundedCornerShape(topEnd = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(154, 223, 240, 255))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(3f)
                    .padding(8.dp),
            ){
                Text(text = "DEPART", style = MaterialTheme.typography.labelMedium)
                AirportInfo(code = chosenAirport , name = chosenAirportName)
                Text(text = "ARRIVE", style = MaterialTheme.typography.labelMedium)
                AirportInfo(code = destinationList, name = destinationName)
            }

            IconButton(
                onClick =
                {
                    if (isFavorite) {
                        removeFavorite()
                    } else {
                        addFavorite()
                    }
                },


                modifier = Modifier
                    .weight(1f)
                    .scale(2f),
                ) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Favorite",
                    tint = if (isFavorite) Color.Yellow else Color.DarkGray
                )
            }
        }

    }

}


@Preview
@Composable
fun QueryResultScreenPreview(){
    FlightDepartDestinationCard(
        chosenAirport = "JFK",
        chosenAirportName = "John F. Kennedy",
        destinationList = "LAX, SFO",
        destinationName = "Los Angeles, San Francisco",
        addFavorite = {},
        removeFavorite = {},
    )
}