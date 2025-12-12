package com.app.gameshelf.ui.navigation.routes

sealed class Route(val route: String) {

    object Signup : Route("signup")
    object Home : Route("home")
    object Search : Route("search")
    object News : Route("news")
    object Profile : Route("profile")

    // Routes for each screen
    object Game : Route("game/{gameId}") {
        fun createRoute(gameId: String) = "game/$gameId"
    }
    object GameMoreInfo : Route("game/{gameId}/{category}/{name}/more-info") {
        fun createRoute(gameId: String, category: String, name: String) = "game/$gameId/$category/$name/more-info"
    }

    // TODO: Add more routes
    object UserProfile : Route("user/{userId}") {
        fun createRoute(userId: String) = "user/$userId"
    }
}