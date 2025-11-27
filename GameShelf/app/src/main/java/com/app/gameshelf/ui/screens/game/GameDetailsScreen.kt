package com.app.gameshelf.ui.screens.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.app.gameshelf.ui.components.achievementCard.AchievementCard
import com.app.gameshelf.ui.components.backButton.backButton

@Composable
fun GameDetailsScreen(
    gameId: String,
    category: String,
    onBackClick: () -> Unit
){
    val imageUrl = remember(gameId) {
        "https://cdn.cloudflare.steamstatic.com/steam/apps/${gameId}/header.jpg"
    }

    AsyncImage(
        modifier = Modifier
            .fillMaxWidth()
            .blur(2.dp)
            .height(290.dp),
        model = imageUrl,
        contentDescription = "Game Banner",
        contentScale = ContentScale.Crop,
        alignment = androidx . compose . ui . BiasAlignment(horizontalBias = -0.5f, verticalBias = 0f)
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
        ){
            when (category) {
                "conquistas" -> {
                    items(3) {
                        AchievementCard(
                            unLockedet = true,
                            urlImage = "https://steamcdn-a.akamaihd.net/steamcommunity/public/images/apps/2161700/72dcd4e4707d8feb2fe31ebabeec7cf177dc1c8e.jpg",
                            hidden = false,
                            name = "Poder Despertado",
                            description = "Obteve Orfeu.(Somente na história principal de Persona 3 Reload)"
                        )
                    }
                    items(2){
                        AchievementCard(
                            unLockedet = false,
                            urlImage = "https://steamcdn-a.akamaihd.net/steamcommunity/public/images/apps/2161700/72dcd4e4707d8feb2fe31ebabeec7cf177dc1c8e.jpg",
                            hidden = false,
                            name ="Conquista bloqueada",
                            description = "teste de conquista bloqueada"
                        )
                    }
                    items(1){
                        AchievementCard(
                            unLockedet = false,
                            urlImage = "https://steamcdn-a.akamaihd.net/steamcommunity/public/images/apps/2161700/72dcd4e4707d8feb2fe31ebabeec7cf177dc1c8e.jpg",
                            hidden = true,
                            name ="Conquista bloqueada",
                            description = "teste de conquista bloqueada"
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
