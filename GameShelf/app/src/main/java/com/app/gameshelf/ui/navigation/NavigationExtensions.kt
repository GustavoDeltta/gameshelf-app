package com.app.gameshelf.ui.navigation

import androidx.navigation.NavHostController

// Back button
fun NavHostController.goBack() {
    navigateUp()
}

// Back button with inclusive
fun NavHostController.popBackTo(route: String, inclusive: Boolean = false) {
    popBackStack(route, inclusive)
}