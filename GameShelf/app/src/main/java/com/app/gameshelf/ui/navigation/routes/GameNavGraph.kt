package com.app.gameshelf.ui.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.app.gameshelf.ui.screens.game.GameScreen
import com.app.gameshelf.ui.screens.game.GameDetailsScreen
import com.app.gameshelf.ui.navigation.popBackTo

fun NavGraphBuilder.gameNavGraph(navController: NavHostController) {
    composable(Route.Game.route) { backStackEntry ->
        val gameId = backStackEntry.arguments?.getString("gameId") ?: ""

        GameScreen(
            gameId = gameId,
            navController = navController,
            onBackClick = { navController.popBackTo(Route.Search.route) },
            onGameDetailsClick = { category ->
                navController.navigate(Route.GameMoreInfo.createRoute(gameId, category))
            }
        )
    }

    composable(Route.GameMoreInfo.route) { backStackEntry ->
        val gameId = backStackEntry.arguments?.getString("gameId") ?: ""
        val category = backStackEntry.arguments?.getString("category") ?: ""

        GameDetailsScreen(
            gameId = gameId,
            category = category,
            onBackClick = { navController.navigateUp() }
        )
    }
}