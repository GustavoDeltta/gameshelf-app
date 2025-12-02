package com.app.gameshelf.ui.screens.news

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@Composable
fun NewsScreen(navController: NavController) {
    Scaffold { pad ->
        Row(
            modifier = Modifier.fillMaxSize().padding(pad).background(Color.Gray)
        ) {
            Text("News")
        }
    }
}