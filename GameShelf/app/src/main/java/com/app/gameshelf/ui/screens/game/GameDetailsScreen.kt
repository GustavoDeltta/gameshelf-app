package com.app.gameshelf.ui.screens.game

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun GameDetailsScreen(
    gameId: String,
    category: String,
    onBackClick: () -> Unit
){
    Text("Você está em ${category}")


}