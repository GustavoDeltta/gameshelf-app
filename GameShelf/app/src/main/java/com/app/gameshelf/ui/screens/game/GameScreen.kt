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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.app.gameshelf.ui.components.backButton.backButton
import com.app.gameshelf.R
import com.app.gameshelf.ui.components.buttonAddTo.ButtonAddTo
import com.app.gameshelf.ui.components.highLightAchievements.highLightAchievements
import com.app.gameshelf.ui.screens.gameDetails.GameDetailsViewModel
import com.app.gameshelf.ui.screens.gameDetails.GameViewModel

@Composable
fun GameScreen(
    gameId: String,
    navController: NavController,
    onBackClick: () -> Unit,
    onGameDetailsClick: (String, String) -> Unit,
    detailsViewModel: GameDetailsViewModel = viewModel()
){
    val detailsState by detailsViewModel.uiState.collectAsState()
    val scrollVertical = rememberScrollState()

    LaunchedEffect(gameId) {
        detailsViewModel.loadGameDetails(gameId)
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollVertical),
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(290.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .blur(2.dp)
                    .height(290.dp),
                model = detailsState.gameData?.background,
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
                                MaterialTheme.colorScheme.background.copy(alpha = 0.3f),
                                MaterialTheme.colorScheme.background.copy(alpha = 1f)
                            )
                        )
                    )
            )

            backButton(onBackClick = onBackClick)

            Column(
                modifier = Modifier
                    .padding(top = 120.dp)
                    .padding(horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .aspectRatio(2f / 3f),
                        shape = RoundedCornerShape(0.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        AsyncImage(
                            model = detailsState.gameData?.cover,
                            contentDescription = "Game cover",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 16.dp),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Text(
                            text = detailsState.gameData?.name ?: "",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Text(
                            "2024 - ATLUS",
                            style = MaterialTheme.typography.titleMedium,
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = detailsState.gameData?.description ?: "",
                            maxLines = 4,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                            textAlign = TextAlign.Justify,
                        )
                    }
                }
            }
        }

        // --------------------------------
        //  Statistics of list, Players and Rating
        // --------------------------------

        Column(
            Modifier
                .fillMaxWidth()
                .padding(top = 5.dp)
                .padding(horizontal = 16.dp)
        ) {
            // Button backlog
            val Status: String = "completed"
            ButtonAddTo(Status)

            // Developers and Publishers
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = MaterialTheme.shapes.medium
                    ),
                    horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    Modifier
                        .padding(16.dp)
                        .weight(0.5f)
                ) {
                    Text(
                        "Desenvolvedora",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                    )

                    Text(
                        text = detailsState.gameData?.developers?.get(0) ?: "",
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(0.5f),
                    horizontalAlignment = androidx.compose.ui.Alignment.End
                ) {
                    Text(
                        "Distribuidora",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                    )
                    Text(
                        text = detailsState.gameData?.publishers?.get(0) ?: "",
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.End
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
                            .clickable {
                                onGameDetailsClick(
                                    info.category,
                                    detailsState.gameData?.name ?: ""
                                )
                            }
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
                .padding(vertical = 15.dp),
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
                    modifier = Modifier.clickable {
                        onGameDetailsClick(
                            "conquistas",
                            detailsState.gameData?.name ?: ""
                        )
                    }
                )
            }

            // --------------------------------
            //  Achievements
            // --------------------------------

            highLightAchievements(
                onClickListener = {
                    onGameDetailsClick(
                        "conquistas",
                        detailsState.gameData?.name ?: ""
                    )
                },
                unLocked = "56",
                locked = "56",
                progress = 0.0f,
                lastUnlockedImage = "https://steamcdn-a.akamaihd.net/steamcommunity/public/images/apps/2161700/6e7494b675cda9e4e49c4f69b6db861215297875.jpg",
                lastUnlockedName = "Um Novo Laço",
                lastUnlockedDescription = "Faça uma nova amizade e descubra um novo arcano",
                listOfLastUnlocked = listOf(
                    "https://cdn.fastly.steamstatic.com/steamcommunity/public/images/apps/2161700/0e663e02f247d96c7831a475cb94207c915edef7.jpg",
                    "https://cdn.fastly.steamstatic.com/steamcommunity/public/images/apps/2161700/72dcd4e4707d8feb2fe31ebabeec7cf177dc1c8e.jpg",
                    "https://cdn.fastly.steamstatic.com/steamcommunity/public/images/apps/2161700/0e6f407133cc515e57f87beb86bd249f88c1465e.jpg",
                    "https://cdn.fastly.steamstatic.com/steamcommunity/public/images/apps/2161700/266b23707fa588742cab527fd146d48630a3548f.jpg",
                    "https://cdn.fastly.steamstatic.com/steamcommunity/public/images/apps/2161700/d449b1bd7151fa69e3d34e9b42bd3142a8cbb8a4.jpg",
                )
            )

            // --------------------------------
            //  Rating
            // --------------------------------

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 15.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Notas",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(end = 20.dp)
                )

                HorizontalDivider(thickness = 2.dp)
            }
        }


    }
}