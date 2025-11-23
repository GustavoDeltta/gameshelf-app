package com.app.gameshelf.ui.screens.game

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.app.gameshelf.ui.components.backButton.backButton
import com.app.gameshelf.R
import com.app.gameshelf.ui.components.buttonAddTo.ButtonAddTo

@Composable
fun GameScreen(
    gameId: String,
    navController: NavController,
    onBackClick: () -> Unit,
    onGameDetailsClick: (String) -> Unit
){
    val imageUrl = remember(gameId) {
        "https://cdn.cloudflare.steamstatic.com/steam/apps/${gameId}/header.jpg"
    }
    val coverUrl = remember(gameId) {
        "https://cdn.cloudflare.steamstatic.com/steam/apps/$gameId/library_600x900.jpg"
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
                        MaterialTheme.colorScheme.background.copy(alpha = 0.3f),
                        MaterialTheme.colorScheme.background.copy(alpha = 1f)
                    )
                )
            )
    )

    backButton(onBackClick = onBackClick)

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
            Card(
                modifier = Modifier
                    .aspectRatio(2f / 3f),
                shape = RoundedCornerShape(0.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                AsyncImage(
                    model = coverUrl,
                    contentDescription = "Game cover",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    //placeholder = androidx.compose.ui.res.painterResource(R.drawable.placeholder),
                    //error = androidx.compose.ui.res.painterResource(R.drawable.placeholder)
                )
            }

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
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Justify,
                )
            }
        }

        // Button backlog
        val Status: String = "completed"
        ButtonAddTo(Status)

        // Hours statistics
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = MaterialTheme.shapes.medium
                ),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(
                    "Horas jogadas:",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                )
                Text(
                    "200 Horas",
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Column(
                modifier = Modifier
                    .padding(16.dp),
                horizontalAlignment = androidx.compose.ui.Alignment.End
            ) {
                Text(
                    "Última sessão:",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                )
                Text(
                    "4 de nov",
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        // Statistics of list, evaluation and players
        data class InfoItem(
            val quant: String,
            val category: String,
            val Description: String,
            val iconRes: Int,
            val color: Color
        )

        val infoList = listOf(
            InfoItem(
                quant = "1.2k",
                category = "Lista",
                Description = "Jogos que estão na sua lista",
                iconRes = R.drawable.ic_list,
                color = MaterialTheme.colorScheme.secondary
            ),
            InfoItem(
                quant = "1.9k",
                category = "Avaliações",
                Description = "Avaliações de jogos",
                iconRes = R.drawable.ic_reviews,
                color = MaterialTheme.colorScheme.onPrimary
            ),
            InfoItem(
                quant = "26k",
                category = "Jogadores",
                Description = "Jogadores que jogaram",
                iconRes = R.drawable.ic_control,
                color = MaterialTheme.colorScheme.tertiary
            )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            infoList.forEach { info ->
                Column(
                    modifier = Modifier
                        .height(80.dp)
                        .clickable { onGameDetailsClick(info.category) }
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = MaterialTheme.shapes.medium
                        )
                        .weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
                ) {
                    Text(
                        info.quant,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    )

                    Row(
                        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = androidx.compose.ui.res.painterResource(info.iconRes),
                            contentDescription = info.Description,
                            tint = info.color,
                            modifier = Modifier
                                .size(20.dp)
                                .padding(end = 3.dp)
                        )

                        Text(
                            info.category,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Conquistas",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium,
            )

            Text(
                "ver todas",
                style = MaterialTheme.typography.labelMedium,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                modifier = Modifier.clickable{onGameDetailsClick("conquistas")}
            )
        }
    }
}