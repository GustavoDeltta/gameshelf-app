package com.app.gameshelf.ui.screens.home

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(){
    Scaffold { pad ->
        Row(
            modifier = Modifier.padding(pad)
        ) {
            Text("Home")
        }
    }
}