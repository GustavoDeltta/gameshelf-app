package com.app.gameshelf.ui.components.reviewCard

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.app.gameshelf.R

@Composable
fun ReviewCardWithoutCover(
    profilePicture: String,
    profileName: String,
    backlog: String,
    allAchievementsUnlocked: Boolean = false,
    hoursPlayed: Float,
    reviewScore: Float,
    reviewComent: String,
){
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .clip(ShapeDefaults.Small)
            .background(MaterialTheme.colorScheme.surface),

    ){
        // Profile Picture and infos ---------------------------
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
            ){
                AsyncImage(
                    model = profilePicture,
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .fillMaxSize(),
                )
            }

            // Name and badge ------------------------------
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = profileName,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 16.sp
                )


                // Badge area -----------------------------
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp),
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {

                    val color: Color
                    val icon: Painter

                    when (backlog) {
                        "completed" -> {
                            color = MaterialTheme.colorScheme.onSecondary
                            icon = painterResource(R.drawable.ic_check)
                        }
                        "playing" -> {
                            color = MaterialTheme.colorScheme.onPrimary
                            icon = painterResource(R.drawable.ic_control)
                        }
                        "backlog" -> {
                            color = MaterialTheme.colorScheme.tertiary
                            icon = painterResource(R.drawable.ic_list)
                        }
                        "dropped" -> {
                            color = MaterialTheme.colorScheme.tertiary
                            icon = painterResource(R.drawable.ic_dropped)
                        }
                        else -> {
                            color = Color.White
                            icon = painterResource(R.drawable.ic_add)
                        }
                    }

                    Row(
                        modifier = Modifier
                            .clip(ShapeDefaults.Small)
                            .background(color)
                            .clip(ShapeDefaults.ExtraLarge)
                            .size(25.dp),
                        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = icon,
                            contentDescription = "platina",
                            modifier = Modifier.size(16.dp),
                            tint = if (backlog == "")Color.Black else Color.White
                        )
                    }

                    // All achievements unlocked badge
                    if (allAchievementsUnlocked) {
                        Row(
                            modifier = Modifier
                                .clip(ShapeDefaults.Small)
                                .background(MaterialTheme.colorScheme.onSecondary)
                                .clip(ShapeDefaults.ExtraLarge)
                                .size(25.dp),
                            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_trophy),
                                contentDescription = "platina",
                                modifier = Modifier.size(16.dp),
                                tint = Color.White
                            )
                        }
                    }

                    // Hours badge
                    Row(
                        modifier = Modifier
                            .clip(ShapeDefaults.Small)
                            .background(Color.DarkGray)
                            .height(25.dp)
                            .padding(start = 7.dp, end = 7.dp),
                        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "$hoursPlayed h",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.White,
                            fontSize = 12.sp
                        )
                    }

                }
            }
        }

        // Star and review ---------------------------------
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 10.dp)
        ) {
            Column (
                modifier = Modifier
                    .width(56.dp)
                    .height(70.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
            ){
                Icon(
                    painter = painterResource(R.drawable.ic_star_regular),
                    contentDescription = "star",
                    tint = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.size(25.dp)
                )

                Text(
                    text = reviewScore.toString(),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                )
            }
            Text(
                text = reviewComent,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 13.sp,
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}


