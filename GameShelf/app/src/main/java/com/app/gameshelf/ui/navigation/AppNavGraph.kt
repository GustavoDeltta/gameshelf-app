package com.app.gameshelf.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.app.gameshelf.ui.screens.home.HomeScreen
import com.app.gameshelf.ui.screens.news.NewsScreen
import com.app.gameshelf.ui.screens.profile.ProfileScreen
import com.app.gameshelf.ui.screens.search.SearchScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavGraph(navController: NavHostController) {
    AnimatedNavHost(
        navController = navController,
        startDestination = "home",
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
        composable("home") { HomeScreen() }
        composable("search") { SearchScreen() }
        composable("news") { NewsScreen() }
        composable("profile") { ProfileScreen() }
    }
}
