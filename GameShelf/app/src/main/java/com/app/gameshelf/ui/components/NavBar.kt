package com.app.gameshelf.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.MaterialTheme
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
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { route ->
            val selected = currentRoute == route
            Row (
                modifier = Modifier
                    .clip(CircleShape)
                    .height(50.dp)
                    .background(if (selected) MaterialTheme.colorScheme.primary else Color.Transparent)
                    .border(width = 1.dp, color = MaterialTheme.colorScheme.primary, shape = CircleShape)
                    .then(
                        if (selected) Modifier.weight(1f) else Modifier.width(50.dp)
                    )
                    .clickable {
                        if (!selected) navController.navigate(route) {
                            popUpTo("home") { inclusive = false }
                            launchSingleTop = true
                        }
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Icon(
                    modifier = Modifier.size(25.dp),
                    imageVector = when (route) {
                        "home" -> Icons.Default.Home
                        "search" -> Icons.Default.Search
                        "news" -> Icons.Default.Add
                        "profile" -> Icons.Default.AccountCircle
                        else -> Icons.Default.Home
                    },
                    contentDescription = route,
                    tint = if (selected) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.primary
                )

                if (selected){
                    Text(
                        text = when (route) {
                            "home" -> "Inicio"
                            "search" -> "Procurar"
                            "news" -> "Novidades"
                            "profile" -> "Conta"
                            else -> "Inicio"
                        },
                        style = MaterialTheme.typography.labelLarge,
                        color = if (selected) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.primary,
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

