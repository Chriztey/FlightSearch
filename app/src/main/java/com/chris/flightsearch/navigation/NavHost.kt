package com.chris.flightsearch.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.navigation.compose.NavHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

import androidx.navigation.NavHostController
import androidx.navigation.NavType.Companion.StringType
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.chris.flightsearch.R
import com.chris.flightsearch.screen.HomeScreenDestination
import com.chris.flightsearch.screen.MainScreen
import com.chris.flightsearch.screen.QueryResultDestination
import com.chris.flightsearch.screen.QueryResultScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavHost(
    navHostController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val haveBackStackEntry by remember {
        derivedStateOf { navHostController.previousBackStackEntry != null }
    }
//    Scaffold(
//        modifier = Modifier.fillMaxSize(),
//        topBar = {
//            TopAppBar(
//                title = {
//                    Text(text = "Flight Search")
//                },
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
//            )
//        }
//    ) { it ->
        NavHost(
            //modifier = Modifier.padding(it),
            navController = navHostController,
            startDestination = HomeScreenDestination.routeDestination
        ) {
            composable(
                route = HomeScreenDestination.routeDestination
            ) {
                MainScreen {
                    QueryResultDestination.routeName = it
                    navHostController.navigate("${QueryResultDestination.routeDestination}/$it")
                }
            }

            composable(
                route = QueryResultDestination.argRoute,
                arguments = listOf(navArgument(QueryResultDestination.queryArgument) {
                    type = StringType
                })
            ) {
                QueryResultScreen(
                    onNavToQuery = {
                        QueryResultDestination.routeName = it
                        navHostController.navigate("${QueryResultDestination.routeDestination}/$it")
                                   },
                    navBack = {navHostController.navigateUp()})
            }

        }
//    }
}