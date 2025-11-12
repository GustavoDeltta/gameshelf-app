package com.app.gameshelf.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun NavBar(navController: NavController) {
    val items = listOf("home", "search", "news", "profile")
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .clip(CircleShape)
            .background(Color(0xFF1C1C1E))
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { route ->
            val selected = currentRoute == route
            Row (
                modifier = Modifier
                    .clip(CircleShape)
                    .background(if (selected) Color.White else Color.Transparent)
                    .border(width = 1.dp, color = Color.White, shape = CircleShape)
                    .padding(15.dp)
                    .padding(horizontal = if (selected) 15.dp else 0.dp)
                    .clickable {
                        if (!selected) navController.navigate(route) {
                            popUpTo("home") { inclusive = false }
                            launchSingleTop = true
                        }
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    imageVector = when (route) {
                        "home" -> Icons.Default.Home
                        "search" -> Icons.Default.Search
                        "news" -> Icons.Default.Add
                        "profile" -> Icons.Default.AccountCircle
                        else -> Icons.Default.Home
                    },
                    contentDescription = route,
                    tint = if (selected) Color(0xFF2C2C2E) else Color.White
                )

                if (selected){
                    Text(
                        "${route}",
                        color = if (selected) Color(0xFF2C2C2E) else Color.White,
                        modifier = Modifier
                            .padding(start = 10.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun NavBarPreview(){
    val navController = rememberNavController()
    NavBar(navController = navController)
}

