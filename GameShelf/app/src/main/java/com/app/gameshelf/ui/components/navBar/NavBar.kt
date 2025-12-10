package com.app.gameshelf.ui.components.navBar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.app.gameshelf.R

@Composable
private fun NavBarItem(
    route: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val itemWidth by animateDpAsState(
        targetValue = if (selected) 200.dp else 50.dp,
        animationSpec = tween(durationMillis = 250),
        label = "width_animation"
    )

    Row(
        modifier = modifier
            .clip(CircleShape)
            .height(50.dp)
            .then(
                if (!selected) Modifier.width(itemWidth) else Modifier
            )
            .background(if (selected) MaterialTheme.colorScheme.primary else Color.Transparent)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = CircleShape
            )
            .clickable(enabled = !selected) { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Icon(
            modifier = Modifier.size(25.dp),
            painter = when (route) {
                "home" -> painterResource(R.drawable.ic_home)
                "search" -> painterResource(R.drawable.ic_search)
                "profile" -> painterResource(R.drawable.ic_profile)
                else -> painterResource(R.drawable.ic_home)
            },
            contentDescription =when (route) {
                "home" -> stringResource(R.string.home)
                "search" -> stringResource(R.string.search)
                "news" -> "Novidades"
                "profile" -> stringResource(R.string.profile)
                else -> stringResource(R.string.home)
            },
            tint = if (selected) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.primary
        )

        AnimatedVisibility(
            visible = selected,
            enter = fadeIn(animationSpec = tween(200, delayMillis = 50)),
            exit = fadeOut(animationSpec = tween(150))
        ) {
            Text(
                text = when (route) {
                    "home" -> stringResource(R.string.home)
                    "search" -> stringResource(R.string.search)
                    "news" -> "Novidades"
                    "profile" -> stringResource(R.string.profile)
                    else -> stringResource(R.string.home)
                },
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
    }
}

@Composable
fun NavBar(navController: NavController) {
    val items = remember { listOf("home", "search", "news", "profile") }
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    Row(
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

            if (selected) {
                NavBarItem(
                    route = route,
                    selected = true,
                    onClick = {},
                    modifier = Modifier.weight(1f)
                )
            } else {
                NavBarItem(
                    route = route,
                    selected = false,
                    onClick = {
                        navController.navigate(route) {
                            popUpTo("home") { inclusive = false }
                            launchSingleTop = true
                        }
                    },
                    modifier = Modifier
                )
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun NavBarPreview() {
    val navController = rememberNavController()
    NavBar(navController = navController)
}