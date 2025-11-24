package com.app.gameshelf.ui.components.achievementCard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun AchievementCard(){
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(0.dp),
        colors = CardColors(
            contentColor = MaterialTheme.colorScheme.onSurface,
            containerColor = MaterialTheme.colorScheme.surface,
            disabledContainerColor = MaterialTheme.colorScheme.surface,
            disabledContentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        val UrlImage = "https://steamcdn-a.akamaihd.net/steamcommunity/public/images/apps/2161700/72dcd4e4707d8feb2fe31ebabeec7cf177dc1c8e.jpg"

        Row(
            modifier = Modifier
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = UrlImage,
                contentDescription = "Conquista",
                modifier = Modifier
                    .size(75.dp)
            )

            Column(
                modifier = Modifier
                    .padding(start = 20.dp)
            ) {
                Text(
                    "Um Novo Laço",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .padding(bottom = 5.dp)
                )

                Text(
                    "Faça uma nova amizade e descubra um novo arcano",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                    lineHeight = 19.sp,
                )
            }
        }
    }
}
