package com.app.gameshelf.ui.screens.gameDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.app.gameshelf.R
import com.app.gameshelf.data.model.Achievement
import com.app.gameshelf.data.model.AchievementData
import com.app.gameshelf.ui.components.achievementCard.AchievementBottomSheet
import com.app.gameshelf.ui.components.achievementCard.AchievementCard
import com.app.gameshelf.ui.components.backButton.backButton


@Composable
fun GameDetailsScreen(
    gameId: String,
    category: String,
    playerId: String = "76561199157114802",
    onBackClick: () -> Unit,
    viewModel: GameViewModel = viewModel()
) {
    val imageUrl = remember(gameId) {
        "https://cdn.cloudflare.steamstatic.com/steam/apps/${gameId}/header.jpg"
    }

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(gameId, category) {
        if (category == "conquistas") {
            viewModel.loadAchievements(gameId, playerId)
        }
    }

    AsyncImage(
        modifier = Modifier
            .fillMaxWidth()
            .blur(2.dp)
            .height(290.dp),
        model = imageUrl,
        contentDescription = "Game Banner",
        contentScale = ContentScale.Crop,
        alignment = androidx.compose.ui.BiasAlignment(
            horizontalBias = -0.5f,
            verticalBias = 0f
        )
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(290.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.background.copy(alpha = 0.1f),
                        MaterialTheme.colorScheme.background.copy(alpha = 0.5f),
                        MaterialTheme.colorScheme.background.copy(alpha = 1f)
                    )
                )
            )
    )

    backButton(onBackClick = onBackClick)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 110.dp)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Persona 3 Reload",
            style = MaterialTheme.typography.titleLarge,
            fontSize = 30.sp,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            category,
            style = MaterialTheme.typography.titleLarge,
            fontSize = 20.sp,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Normal,
            color = MaterialTheme.colorScheme.primary
        )

        var selectedAchievement by remember { mutableStateOf<Achievement?>(null) }
        var showUnlocked by remember { mutableStateOf(true) }
        var showLocked by remember { mutableStateOf(true) }

        selectedAchievement?.let { achievement ->
            AchievementBottomSheet(
                achievement = achievement,
                gameID = gameId,
                onDismiss = { selectedAchievement = null }
            )
        }

        LazyColumn(
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 70.dp),
        ) {
            when (category) {
                "conquistas" -> {
                    // Show loading
                    if (uiState.isLoading) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(72.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }

                    // Show error
                    if (uiState.error != null) {
                        item {
                            Text(
                                text = "Erro: ${uiState.error}",
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }

                    // Unlocked achievements header
                    if (uiState.unlockedAchievements.isNotEmpty()) {
                        item(key = "unlocked_header") {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Button (
                                    onClick = { showUnlocked = !showUnlocked },
                                    modifier = Modifier
                                        .padding(end = 10.dp)
                                        .height(34.dp),
                                    colors = ButtonColors(
                                        containerColor = MaterialTheme.colorScheme.surface,
                                        contentColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                                        disabledContainerColor = MaterialTheme.colorScheme.background,
                                        disabledContentColor = MaterialTheme.colorScheme.primary
                                    )
                                ) {
                                    Text(
                                        "${uiState.unlockedAchievements.size} Conquistas desbloqueadas",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.padding(end = 15.dp)
                                    )

                                    Icon(
                                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_back),
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier
                                            .then(
                                                if (showUnlocked) Modifier.rotate(270f) else Modifier.rotate(180f)
                                            )
                                    )
                                }

                                HorizontalDivider(thickness = 2.dp)
                            }
                        }
                    }

                    if (showUnlocked) {
                        items(
                            items = uiState.unlockedAchievements,
                            key = { it.name },
                            contentType = { "achievement" }
                        ) { item ->
                            AchievementCard(
                                achievement = item,
                                gameID = gameId,
                                onClick = {
                                    selectedAchievement = item
                                }
                            )
                        }
                    }

                    if (uiState.lockedAchievements.isNotEmpty()) {
                        item(key = "locked_header") {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Button (
                                    onClick = { showLocked = !showLocked },
                                    modifier = Modifier
                                        .padding(end = 10.dp)
                                        .height(34.dp),
                                    colors = ButtonColors(
                                        containerColor = MaterialTheme.colorScheme.surface,
                                        contentColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                                        disabledContainerColor = MaterialTheme.colorScheme.background,
                                        disabledContentColor = MaterialTheme.colorScheme.primary
                                    )
                                ) {
                                    Text(
                                        "${uiState.lockedAchievements.size} Conquistas bloqueadas",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.padding(end = 15.dp)
                                    )

                                    Icon(
                                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_back),
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier
                                            .then(
                                                if (showLocked) Modifier.rotate(270f) else Modifier.rotate(180f)
                                            )
                                    )
                                }

                                HorizontalDivider(thickness = 2.dp)
                            }
                        }
                    }

                    // Locked achievements list
                    if (showLocked) {
                        items(
                            items = uiState.lockedAchievements,
                            key = { it.name },
                            contentType = { "achievement" }
                        ) { item ->
                            AchievementCard(
                                achievement = item,
                                gameID = gameId,
                                onClick = {
                                    selectedAchievement = item
                                }
                            )
                        }
                    }
                }

                "lista" -> {
                    // Conteúdo da tela de lista
                }

                "reviews" -> {
                    // Conteúdo da tela de avaliações
                }

                "jogadores" -> {
                    // Conteúdo da tela de mais informações
                }
            }
        }
    }
}