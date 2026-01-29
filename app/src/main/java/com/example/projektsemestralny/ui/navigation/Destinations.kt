package com.example.projektsemestralny.ui.navigation

sealed class Destination(val route: String) {
    data object Home : Destination("home")
    data object Details : Destination("details")
    
    companion object {
        val HomeRoute = Home.route
        val DetailsRoute = Details.route
    }
}
