package com.app.gameshelf.ui.components.highLightAchievements

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.app.gameshelf.R
import com.app.gameshelf.data.model.lastFive

@Composable
fun HighLightAchievements (
    onClickListener: () -> Unit,
    gameID: String,
    unLocked: Int,
    locked: Int,
    progress: Float,
    lastUnlockedImage: String,
    lastUnlockedName: String,
    lastUnlockedDescription: String,
    listOfLastUnlocked: List<lastFive>
){

    if (unLocked == locked && locked > 0) {
        return allUnlockedAchievements(
            onClickListener = onClickListener,
            unLocked = unLocked
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = onClickListener
            ),
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
                    text = "${unLocked}/${locked}",
                    style = MaterialTheme.typography.labelMedium,
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                )

                LinearProgressIndicator(
                    progress = progress,
                    trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                    color = MaterialTheme.colorScheme.onSecondary,
                    strokeCap = StrokeCap.Round,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp)
                        .height(5.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(60.dp)
                            .background(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                    ) {
                        if (unLocked == 0 || locked == 0){
                            Image(
                                painter = androidx.compose.ui.res.painterResource(R.drawable.ph_without_achievements),
                                contentDescription = null,
                                modifier = Modifier.size(60.dp)
                            )
                        }
                        else {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data("https://steamcdn-a.akamaihd.net/steamcommunity/public/images/apps/${gameID}/${lastUnlockedImage}")
                                    .crossfade(true)
                                    .build(),
                                contentScale = ContentScale.Fit,
                                contentDescription = null,
                                modifier = Modifier.size(60.dp)
                            )
                        }
                    }

                    Column(
                        modifier = Modifier
                            .padding(start = 20.dp)
                            .height(60.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text=
                                if(unLocked == 0 && locked > 0) "Nenhuma conquista desbloqueada"
                                else if (locked == 0) "Sem Conquistas"
                                else lastUnlockedName,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                        )
                        Text(
                            text =
                                if(unLocked == 0 && locked > 0 ) "Jogue e desbloquie conquistas."
                                else if (locked == 0) "Esse jogo não possui conquistas"
                                else lastUnlockedDescription,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                            fontSize = 12.sp,
                            lineHeight = 19.sp,
                            maxLines = 2,
                            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                        )
                    }
                }

                // Last Added Achievement
                if (locked > 0) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 15.dp, bottom = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        for (i in 1..5) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(60.dp)
                                    .then(
                                        if (i == 5) Modifier.background(Color.Black)
                                        else Modifier.background(
                                            color = MaterialTheme.colorScheme.primary.copy(
                                                alpha = 0.1f
                                            )
                                        )
                                    )
                            ) {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data("https://steamcdn-a.akamaihd.net/steamcommunity/public/images/apps/${gameID}/${listOfLastUnlocked[i - 1].img}")
                                        .crossfade(true)
                                        .build(),
                                    contentScale = ContentScale.Fit,
                                    contentDescription = null,
                                    modifier = Modifier.size(60.dp),
                                    alpha =
                                        if (i == 5) 0.5f
                                        else 1f
                                )

                                if (i == 5) {
                                    Text("+32")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun allUnlockedAchievements (
    onClickListener: () -> Unit,
    unLocked: Int,
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClickListener() },
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.onSecondary,
            contentColor = Color.White,
            disabledContainerColor = Color.Gray,
            disabledContentColor = Color.Black
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = androidx.compose.ui.res.painterResource(R.drawable.ic_control),
                    contentDescription = null,
                    modifier = Modifier
                        .size(65.dp)
                        .padding(start = 20.dp),
                )

                Column(
                    modifier = Modifier
                        .padding(start = 20.dp)
                ){
                    Text(
                        text = "PLATINA",
                        style = MaterialTheme.typography.labelMedium,
                        fontSize = 12.sp,
                    )
                    Text(
                        text = "ALCANÇADA",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }

            Column(
                modifier = Modifier
                    .background(Color.White)
                    .height(90.dp)
                    .width(150.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Text(
                    "CONQUISTAS",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                    fontSize = 12.sp,
                )
                Text(
                    "${unLocked}",
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.surface,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }
    }
}

