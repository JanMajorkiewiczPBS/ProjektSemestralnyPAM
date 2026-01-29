package com.example.projektsemestralny.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projektsemestralny.ui.screens.DetailsScreen
import com.example.projektsemestralny.ui.screens.HomeScreen
import com.example.projektsemestralny.viewmodels.HomeViewModel

@Composable
fun NavGraph(homeViewModel: HomeViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Destination.HomeRoute
    ) {
        composable(Destination.HomeRoute) {
            HomeScreen(
                viewModel = homeViewModel,
                onNavigateToDetails = {
                    navController.navigate(Destination.DetailsRoute)
                }
            )
        }
        composable(Destination.DetailsRoute) {
            DetailsScreen(viewModel = homeViewModel)
        }
    }
}

