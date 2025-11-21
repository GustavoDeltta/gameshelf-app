package com.app.gameshelf.ui.screens.game

import android.widget.ImageButton
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.app.gameshelf.ui.components.GameCard

@Composable
fun GameScreen(
    gameId: String,
){
    val imageUrl = remember(gameId) {
        "https://cdn.cloudflare.steamstatic.com/steam/apps/${gameId}/header.jpg"
    }

    AsyncImage(
        modifier = Modifier
            .fillMaxWidth()
            .height(290.dp),
        model = imageUrl,
        contentDescription = "Game Cover",
        contentScale = ContentScale.Crop,
        alignment = androidx . compose . ui . BiasAlignment(horizontalBias = -0.7f, verticalBias = 0f)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(290.dp)
            .blur(1.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.background.copy(alpha = 0.1f),
                        MaterialTheme.colorScheme.background.copy(alpha = 0.3f),
                        MaterialTheme.colorScheme.background.copy(alpha = 0.7f),
                        MaterialTheme.colorScheme.background.copy(alpha = 0.9f),
                        MaterialTheme.colorScheme.background.copy(alpha = 1f)
                    )
                )
            )
    )

    // botão de voltar
    Box(
        modifier = Modifier
                .padding(top = 16.dp, start = 16.dp)
                .width(40.dp)
                .height(40.dp)
                .clickable {
                    //to do
                }
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = MaterialTheme.shapes.small
                )
    ) {
        Text("<")
    }

    Column(
        modifier = Modifier
            .padding(top = 140.dp)
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) {

            GameCard(
                gameId = gameId,
                onGameClick = { id ->
                    //
                }
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    "PERSONA 3 RELOAD",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    "2024 - ATLUS",
                    style = MaterialTheme.typography.titleMedium,
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    "Mergulhe na Hora Sombria e desperte as profundezas do seu coração. Persona 3 Reload é uma reimaginação cativante...",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Justify,
                )
            }
        }
    }
}