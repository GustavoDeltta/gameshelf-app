package com.app.gameshelf.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.app.gameshelf.ui.screens.home.HomeScreen
import com.app.gameshelf.ui.screens.news.NewsScreen
import com.app.gameshelf.ui.screens.profile.ProfileScreen
import com.app.gameshelf.ui.screens.search.SearchScreen
import com.app.gameshelf.ui.screens.signup.SignupScreen
import com.app.gameshelf.ui.navigation.routes.*
import com.app.gameshelf.data.repository.AuthRepository

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavGraph(navController: NavHostController) {
    val context = LocalContext.current
    val authRepository = remember { AuthRepository(context) }
    // Determine start destination based on whether token exists
    val startDestination = if (authRepository.getToken() != null) {
        Route.Home.route
    } else {
        Route.Signup.route
    }

    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = {
            slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
        },
        exitTransition = {
            slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
        },
        popEnterTransition = {
            slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right)
        },
        popExitTransition = {
            slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right)
        }
    ) {
        // Main screens
        composable(Route.Signup.route) {
            SignupScreen(navController = navController)
        }
        composable(Route.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(Route.Search.route) {
            SearchScreen(navController = navController)
        }
        composable(Route.News.route) {
            NewsScreen(navController = navController)
        }
        composable(Route.Profile.route) {
            ProfileScreen(navController = navController)
        }

        // Nav graphs for each screen
        gameNavGraph(navController)
        // TODO: Add more nav graphs
        // profileNavGraph(navController)
        // searchNavGraph(navController)
    }
}