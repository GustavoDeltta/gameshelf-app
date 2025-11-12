package com.app.gameshelf.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ProfileScreen() {
    Scaffold { pad ->
        Row(
            modifier = Modifier.fillMaxSize().background(Color.Blue).padding(pad)
        ) {
            Text("Profile")
        }
    }
}