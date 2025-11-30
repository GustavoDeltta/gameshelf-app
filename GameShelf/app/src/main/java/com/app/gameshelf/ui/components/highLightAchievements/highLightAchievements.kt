package com.app.gameshelf.ui.components.highLightAchievements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun highLightAchievements (){
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = Color.White,
            disabledContainerColor = Color.Gray,
            disabledContentColor = Color.Black
        ),
        elevation = CardDefaults.cardElevation(0.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "43/56",
                    style = MaterialTheme.typography.labelMedium,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                )

                LinearProgressIndicator(
                    progress = 0.6f,
                    trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                    color = MaterialTheme.colorScheme.onSecondary,
                    strokeCap = StrokeCap.Round,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .height(7.dp)
                )

                // Last Added Achievement
                val imageUrl = "https://steamcdn-a.akamaihd.net/steamcommunity/public/images/apps/2161700/0e663e02f247d96c7831a475cb94207c915edef7.jpg"

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(75.dp)
                            .background(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(imageUrl)
                                .crossfade(true)
                                .build(),
                            contentScale = ContentScale.Fit,
                            contentDescription = null,
                            modifier = Modifier.size(75.dp)
                        )
                    }

                    Column(
                        modifier = Modifier
                            .padding(start = 20.dp)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "Um Novo Laço",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium,
                        )
                        Text(
                            "Faça uma nova amizade e descubra um novo arcano",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                            maxLines = 2,
                            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                        )
                    }
                }

                // Last Added Achievement
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(75.dp)
                            .background(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(imageUrl)
                                .crossfade(true)
                                .build(),
                            contentScale = ContentScale.Fit,
                            contentDescription = null,
                            modifier = Modifier.size(75.dp)
                        )
                    }

                    Text("oi")
                }
            }
        }
    }
}