package com.app.gameshelf.ui.components.ratingCard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.gameshelf.R
import com.app.gameshelf.ui.screens.game.SkeletonLoading
import com.app.gameshelf.ui.screens.gameDetails.GameDetailsUiState

@Composable
fun RatingCard(
    reviewScore0to5: Float = 0f,
    progressStar5: Float = 0f,
    progressStar4: Float = 0f,
    progressStar3: Float = 0f,
    progressStar2: Float = 0f,
    progressStar1: Float = 0f,
){
    val reviewScore = reviewScore0to5

    val color: Color = when {
        reviewScore <= 1.5 -> MaterialTheme.colorScheme.primary
        reviewScore <= 2.5 -> MaterialTheme.colorScheme.secondary
        reviewScore <= 3.5 -> MaterialTheme.colorScheme.tertiary
        reviewScore <= 4.5 -> MaterialTheme.colorScheme.onSecondary
        else -> MaterialTheme.colorScheme.onPrimary
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.4f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Total",
                style = MaterialTheme.typography.labelMedium
            )

            Text(
                text = reviewScore.toString(),
                color = color,
                style = MaterialTheme.typography.labelMedium,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
            )
        }

        VerticalDivider( thickness = 2.dp)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            // star 5
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 7.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "5 ",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                    fontSize = 10.sp
                )
                Icon(
                    painter = androidx.compose.ui.res.painterResource(R.drawable.ic_star),
                    contentDescription = "star",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(10.dp)
                )

                // Progress bar
                LinearProgressIndicator(
                    progress = { progressStar5 },
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .weight(1f)
                        .height(12.dp)
                        .clip(RoundedCornerShape(6.dp)),
                    color = MaterialTheme.colorScheme.onPrimary,
                    trackColor = Color(0xFF2A2A2A),
                )
            }

            // star 4
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 7.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "4 ",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                    fontSize = 10.sp
                )
                Icon(
                    painter = androidx.compose.ui.res.painterResource(R.drawable.ic_star),
                    contentDescription = "star",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(10.dp)
                )

                // Progress bar
                LinearProgressIndicator(
                    progress = { progressStar4 },
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .weight(1f)
                        .height(12.dp)
                        .clip(RoundedCornerShape(6.dp)),
                    color = MaterialTheme.colorScheme.onSecondary,
                    trackColor = Color(0xFF2A2A2A),
                )
            }

            // star 3
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 7.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "3 ",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                    fontSize = 10.sp
                )
                Icon(
                    painter = androidx.compose.ui.res.painterResource(R.drawable.ic_star),
                    contentDescription = "star",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(10.dp)
                )

                // Progress bar
                LinearProgressIndicator(
                    progress = { progressStar3 },
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .weight(1f)
                        .height(12.dp)
                        .clip(RoundedCornerShape(6.dp)),
                    color = MaterialTheme.colorScheme.tertiary,
                    trackColor = Color(0xFF2A2A2A),
                )
            }

            // star 2
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 7.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "2 ",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                    fontSize = 10.sp
                )
                Icon(
                    painter = androidx.compose.ui.res.painterResource(R.drawable.ic_star),
                    contentDescription = "star",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(10.dp)
                )

                // Progress bar
                LinearProgressIndicator(
                    progress = { progressStar2 },
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .weight(1f)
                        .height(12.dp)
                        .clip(RoundedCornerShape(6.dp)),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = Color(0xFF2A2A2A),
                )
            }

            // star 1
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "1  ",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                    fontSize = 10.sp
                )
                Icon(
                    painter = androidx.compose.ui.res.painterResource(R.drawable.ic_star),
                    contentDescription = "star",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(10.dp)
                )

                // Progress bar
                LinearProgressIndicator(
                    progress = { progressStar1 },
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .weight(1f)
                        .height(12.dp)
                        .clip(RoundedCornerShape(6.dp)),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = Color(0xFF2A2A2A),
                )
            }
        }
    }
}