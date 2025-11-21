package com.app.gameshelf.ui.components.navBar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.app.gameshelf.R

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

            // Anmation card
            val itemWidth by animateDpAsState(
                targetValue = if (selected) 200.dp else 50.dp,
                animationSpec = tween(durationMillis = 300),
                label = "width_animation"
            )

            // Animation icon
            val iconScale by animateFloatAsState(
                targetValue = if (selected) 1.1f else 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMedium
                ),
                label = "icon_scale"
            )

            Row (
                modifier = Modifier
                    .clip(CircleShape)
                    .height(50.dp)
                    .background(if (selected) MaterialTheme.colorScheme.primary else Color.Transparent)
                    .border(width = 1.dp, color = MaterialTheme.colorScheme.primary, shape = CircleShape)
                    .then(
                        if (selected) Modifier.weight(1f) else Modifier.width(itemWidth)
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
                    modifier = Modifier
                        .size(25.dp)
                        .graphicsLayer {
                            scaleX = iconScale
                            scaleY = iconScale
                        },
                    painter = when (route) {
                        "home" -> painterResource(R.drawable.ic_home)
                        "search" -> painterResource(R.drawable.ic_search)
                        "news" -> painterResource(R.drawable.ic_news)
                        "profile" -> painterResource(R.drawable.ic_profile)
                        else -> painterResource(R.drawable.ic_home)
                    },
                    contentDescription = route,
                    tint = if (selected) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.primary
                )

                // Animation Text
                AnimatedVisibility(
                    visible = selected,
                    enter = fadeIn(animationSpec = tween(350, delayMillis = 100)) +
                            expandHorizontally(animationSpec = tween(350)) +
                            slideInHorizontally(
                                animationSpec = tween(350),
                                initialOffsetX = { -40 }
                            ),
                    exit = fadeOut(animationSpec = tween(200)) +
                            shrinkHorizontally(animationSpec = tween(250)) +
                            slideOutHorizontally(
                                animationSpec = tween(200),
                                targetOffsetX = { -40 }
                            )
                ) {
                    Text(
                        text = when (route) {
                            "home" -> "Inicio"
                            "search" -> "Procurar"
                            "news" -> "Novidades"
                            "profile" -> "Conta"
                            else -> "Inicio"
                        },
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.surface,
                        modifier = Modifier.padding(start = 10.dp)
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