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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.app.gameshelf.ui.components.backButton.backButton

@Composable
fun GameScreen(
    gameId: String,
    NavController: NavController
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

    backButton(navigation = NavController)

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

        // botao de backlog ( componente )
        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .height(60.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = MaterialTheme.shapes.medium)
        ) {
            Text(
                "+ Adicionar á",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.surface
            )
        }

        // estatistica de horas
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = MaterialTheme.shapes.medium),
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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .height(80.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = MaterialTheme.shapes.medium)
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
            ) {
                Text(
                    "1.2k",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 20.sp
                )

                Row(
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                ){
                    Icon(
                        painter = androidx.compose.ui.res.painterResource(id = com.app.gameshelf.R.drawable.ic_home),
                        contentDescription = "Lista de desejos",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(20.dp).padding(end = 3.dp)
                    )

                    Text(
                        "Lista",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .height(80.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = MaterialTheme.shapes.medium)
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
            ) {
                Text(
                    "1.9k",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 20.sp
                )

                Row(
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                ){
                    Icon(
                        painter = androidx.compose.ui.res.painterResource(id = com.app.gameshelf.R.drawable.ic_home),
                        contentDescription = "Avaliações",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(20.dp).padding(end = 3.dp)
                    )

                    Text(
                        "Avaliações",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .height(80.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = MaterialTheme.shapes.medium)
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
            ) {
                Text(
                    "26k",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 20.sp
                )

                Row(
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                ){
                    Icon(
                        painter = androidx.compose.ui.res.painterResource(id = com.app.gameshelf.R.drawable.ic_home),
                        contentDescription = "Jogadores",
                        tint = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.size(20.dp).padding(end = 3.dp)
                    )

                    Text(
                        "Jogadores",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                    )
                }
            }


        }
    }
}