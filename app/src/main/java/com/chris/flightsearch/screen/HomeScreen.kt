package com.chris.flightsearch.screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chris.flightsearch.viewmodel.AirportViewModel
import com.chris.flightsearch.viewmodel.AppViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chris.flightsearch.component.SearchBar
import com.chris.flightsearch.navigation.Nav
import kotlinx.coroutines.launch

object HomeScreenDestination: Nav{
    override val routeDestination: String = "home_screen"
    override val routeName: String = "Flight Search"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: AirportViewModel = viewModel(
        factory = AppViewModelProvider.Factory),
    onNavToQuery: (String) -> Unit
) {
    val mainScreenUiState by viewModel.uiState.collectAsState()
    val destinationList by viewModel.destinationAirportList.collectAsState()
    val favoriteList by viewModel.favoriteRouteList.collectAsState()
    //val searchQueryKey by viewModel.searchQueryKey.collectAsState()
    val scope = rememberCoroutineScope()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Flight Search")
                },
//                navigationIcon = {
//                    if (haveBackStackEntry) {
//                        IconButton(onClick = {navHostController.navigateUp()}) {
//                            Icon(
//                                imageVector = Icons.Default.ArrowBack,
//                                contentDescription = "Back"
//                            )
//                        }
//                    }
//
//                }
            )
        }
    ) { it ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(it),
        ) {

            var focus by remember { mutableStateOf(false) }
            Log.d("TAG", focus.toString())

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                SearchBar(
                    viewModel = viewModel,
                    airportSearchQuery = mainScreenUiState.airportSearchQuery,
                    onFocusChange = { focus = it },
                    focus = focus,
                    airportQueryList = mainScreenUiState.airportQueryList,
                    onNavToQuery = { onNavToQuery(it) },
                    scope = scope,
                )

                if (favoriteList.list.isNotEmpty()) {
                    Text(
                        text = "Favorites",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    LazyColumn(
                        modifier = Modifier.padding(vertical = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        items(
                            favoriteList.list,
                            key = { it.id }
                        ) { route ->
                            val isFavorite = favoriteList.list.any {
                                it.departureCode == route.departureCode &&
                                        it.destinationCode == route.destinationCode
                            }
                            val unFav = favoriteList.list.find {
                                it.departureCode == route.departureCode &&
                                        it.destinationCode == route.destinationCode
                            }

                            val departureName = destinationList.list.find {
                                it.iataCode == route.departureCode
                            }

                            val destinationName = destinationList.list.find {
                                it.iataCode == route.destinationCode
                            }

                            FlightDepartDestinationCard(
                                chosenAirport = route.departureCode,
                                chosenAirportName = departureName?.name ?: "",
                                destinationList = route.destinationCode,
                                destinationName = destinationName?.name ?: "",
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
            }
        }
    }
}

@Composable
fun AirportInfo(
    code: String,
    name: String,
    onClickQuery: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .clickable {
                onClickQuery()
            }
            .padding(bottom = 8.dp),
    ) {
        Text(
            text = code,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier
            .width(8.dp))
        Text(
            text = name,
            color = Color.Gray
            )
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    AirportInfo(code = "ABC", name = "Aish Beheh Cehe Airport", onClickQuery = {})
}

