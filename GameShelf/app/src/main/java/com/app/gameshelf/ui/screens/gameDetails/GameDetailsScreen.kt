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
import androidx.compose.material3.ButtonDefaults.buttonColors
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.app.gameshelf.R
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
            .blur(10.dp)
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
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Text(
            "Persona 3 Reload",
            style = MaterialTheme.typography.titleLarge,
            fontSize = 30.sp,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            "$category",
            style = MaterialTheme.typography.titleLarge,
            fontSize = 20.sp,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Normal,
            color = MaterialTheme.colorScheme.primary
        )

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

                    // Unlocked achievements
                    if (uiState.achievements.any { it.achieved }) {
                        item {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    "Conquista desbloqueadas",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(end = 10.dp)
                                )

                               HorizontalDivider(thickness = 2.dp)
                            }
                        }
                    }

                    // Load unlocked achievements
                    items(uiState.achievements.filter { it.achieved }){
                        AchievementCard(
                            unlocked = it.achieved,
                            urlImage = "https://steamcdn-a.akamaihd.net/steamcommunity/public/images/apps/${gameId}/${it.icon}",
                            hidden = it.hidden,
                            name = it.name,
                            description = it.description,
                            playerPercentUnlocked = "${it.playerPercentUnlocked}%",
                            playerPercentUnlockedToFloat = it.playerPercentUnlockedToFloat()
                        )
                    }

                    // locked achievements
                    if (uiState.achievements.any { !it.achieved }) {
                        item {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    "Conquista bloqueadas",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(end = 10.dp)
                                )

                                HorizontalDivider(thickness = 2.dp)
                            }
                        }
                    }

                    // Carregar apenas os bloqueados
                    items(uiState.achievements.filter { it.achieved == false }){
                        AchievementCard(
                            unlocked = it.achieved,
                            urlImage = "https://steamcdn-a.akamaihd.net/steamcommunity/public/images/apps/${gameId}/${it.icon}",
                            hidden = it.hidden,
                            name = it.name,
                            description = it.description,
                            playerPercentUnlocked = "${it.playerPercentUnlocked}%",
                            playerPercentUnlockedToFloat = it.playerPercentUnlockedToFloat()
                        )
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