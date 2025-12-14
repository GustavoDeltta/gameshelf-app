package com.app.gameshelf.ui.components.highLightAchievements

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Path
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
                                if(unLocked == 0 && locked > 0) stringResource(R.string.noAchievementsUnlocked)
                                else if (locked == 0) stringResource(R.string.withoutAchievements)
                                else lastUnlockedName,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                        )
                        Text(
                            text =
                                if(unLocked == 0 && locked > 0 ) stringResource(R.string.noAchievementsUnlockedDescription)
                                else if (locked == 0) stringResource(R.string.withoutAchievementsDescription)
                                else lastUnlockedDescription,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                            fontSize = 12.sp,
                            lineHeight = 15.sp,
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
                                    val x = if (unLocked > 1) locked - 5 else locked - 4
                                    Text("+${x}")
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
fun allUnlockedAchievements(
    onClickListener: () -> Unit,
    unLocked: Int,
) {
    // Animação infinita para o gradiente
    val infiniteTransition = rememberInfiniteTransition(label = "shimmer")
    val offsetX by infiniteTransition.animateFloat(
        initialValue = -1f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClickListener() },
        colors = CardColors(
            containerColor = Color.Transparent,
            contentColor = Color.White,
            disabledContainerColor = Color.Gray,
            disabledContentColor = Color.Black
        )
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Background com gradiente animado
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
            ) {
                val gradient = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF4A7FD6),
                        Color(0xFF6B9FE8),
                        Color(0xFF4A7FD6)
                    ),
                    start = Offset(size.width * offsetX, 0f),
                    end = Offset(size.width * (offsetX + 1f), 0f)
                )
                drawRect(brush = gradient)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_control),
                        contentDescription = null,
                        modifier = Modifier
                            .size(65.dp)
                            .padding(start = 20.dp),
                    )

                    Column(
                        modifier = Modifier.padding(start = 20.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.platinum),
                            style = MaterialTheme.typography.labelMedium,
                            fontSize = 12.sp,
                        )
                        Text(
                            text = stringResource(R.string.achieved),
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                }

                // Box com o ângulo cortado
                Box(
                    modifier = Modifier
                        .height(90.dp)
                        .width(150.dp)
                ) {
                    Canvas(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        val angleSize = 25.dp.toPx() // Tamanho do corte diagonal
                        val path = Path().apply {
                            // Começa no ponto após o corte diagonal
                            moveTo(angleSize, 0f)
                            // Linha até o canto superior direito
                            lineTo(size.width, 0f)
                            // Linha até o canto inferior direito
                            lineTo(size.width, size.height)
                            // Linha até o canto inferior esquerdo
                            lineTo(0f, size.height)
                            // Linha até onde começa o corte diagonal
                            lineTo(0f, angleSize)
                            // Linha diagonal que cria o corte
                            lineTo(angleSize, 0f)
                            close()
                        }
                        drawPath(
                            path = path,
                            color = Color.White
                        )
                    }

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            stringResource(R.string.achievements).uppercase(),
                            style = MaterialTheme.typography.labelMedium,
                            color = Color(0xFF666666),
                            fontSize = 12.sp,
                        )
                        Text(
                            "$unLocked",
                            fontSize = 16.sp,
                            style = MaterialTheme.typography.labelMedium,
                            color = Color(0xFF333333),
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                }
            }
        }
    }
}

