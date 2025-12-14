package com.app.gameshelf.ui.components.achievementCard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.app.gameshelf.R
import com.app.gameshelf.data.model.Achievement

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AchievementCard(
    achievement: Achievement,
    gameID: String,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    val imageRequest = remember(achievement.icon) {
        ImageRequest.Builder(context)
            .data("https://steamcdn-a.akamaihd.net/steamcommunity/public/images/apps/${gameID}/${achievement.icon}")
            .crossfade(true)
            .build()
    }

    val unlocked = achievement.achieved
    val hidden = achievement.hidden

    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .border(
                if (!unlocked || hidden) 2.dp else 0.dp,
                MaterialTheme.colorScheme.surface,
                RoundedCornerShape(0.dp)
            ),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (unlocked) MaterialTheme.colorScheme.surface
            else MaterialTheme.colorScheme.background
        )
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 10.dp)
                .padding(start = 10.dp, end = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(75.dp)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {

                // Image
                if (!hidden || unlocked) {
                    AsyncImage(
                        model = imageRequest,
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(75.dp)
                    )
                }

                Icon(
                    painter =
                        if(hidden == true && unlocked == false )painterResource(id = R.drawable.ic_quest)
                        else painterResource(id = R.drawable.ic_lock),
                    contentDescription = null,
                    tint =
                        if(hidden == true && unlocked == false ) MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                        else Color.Transparent,
                    modifier = Modifier.size(30.dp)
                )
            }

            Column(
                modifier = Modifier.padding(start = 20.dp, end = 10.dp)
            ) {
                Text(
                    text =
                        if (!hidden || unlocked) achievement.name
                        else stringResource(R.string.hiddenAchievement),
                    style = MaterialTheme.typography.titleMedium,
                    color =
                        if (!unlocked) MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                        else MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(bottom = 5.dp)
                )

                Text(
                    text =
                        if (!hidden || unlocked) achievement.description
                        else stringResource(R.string.hiddenAchievementDescription),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                    lineHeight = 19.sp,
                )
            }
        }
    }
}