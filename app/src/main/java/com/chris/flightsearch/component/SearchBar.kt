package com.chris.flightsearch.component

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.chris.flightsearch.model.Airport
import com.chris.flightsearch.screen.AirportInfo
import com.chris.flightsearch.viewmodel.AirportViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun SearchBar(
    viewModel: AirportViewModel,
    airportSearchQuery: String,
    onFocusChange: (Boolean) -> Unit,
    focus: Boolean,
    airportQueryList: List<Airport>?,
    onNavToQuery: (String) -> Unit,
    scope: CoroutineScope,
) {

    Box(
        modifier = Modifier.padding(8.dp)
    ) {
        val focusManager = LocalFocusManager.current
        Column {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged {
                        if (it.isFocused) {
                            onFocusChange(true)
                            viewModel.searchAirport(airportSearchQuery)
                        }
                                    },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search")
                },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(32.dp),
                maxLines = 1,
                value = airportSearchQuery,
                onValueChange = {
                    onFocusChange(true)
                    viewModel.updateQuery(it)
                    viewModel.searchAirport(it)
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        onFocusChange(false)
                    }
                )
            )

            if (focus) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RectangleShape,
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    if (airportQueryList.isNullOrEmpty()) {
                        Text(text = "No results")
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .clickable {
                                    onFocusChange(false)
                                    focusManager.clearFocus()
                                }
                            ,
                        ) {
                            items(airportQueryList!!) { airport ->
                                AirportInfo(
                                    code = airport.iataCode,
                                    name = airport.name,
                                    onClickQuery = {
                                        scope.launch {
                                            onNavToQuery(airport.iataCode)
                                        }
                                    }
                                )
                            }
                        }


                    }


                }

            }



        }

    }

}