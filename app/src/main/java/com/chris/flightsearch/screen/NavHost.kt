package com.chris.flightsearch.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.navigation.compose.NavHost
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.NavType.Companion.StringType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun AppNavHost(
    navHostController: NavHostController = rememberNavController(),
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { it ->
        NavHost(
            modifier = Modifier.padding(it),
            navController = navHostController,
            startDestination = HomeScreenDestination.routeDestination
        ) {
            composable(
                route = HomeScreenDestination.routeDestination) {
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
                QueryResultScreen{
                    QueryResultDestination.routeName = it
                    navHostController.navigate("${QueryResultDestination.routeDestination}/$it")
                }
            }

        }
    }
}