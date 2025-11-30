package com.app.gameshelf.ui.components.achievementCard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.app.gameshelf.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AchievementCard(
    unlocked: Boolean = false,
    urlImage: String,
    hidden: Boolean = false,
    name: String,
    description: String,
    playerPercentUnlocked: String,
    playerPercentUnlockedToFloat: Float = 0f,
){

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    Card(
        elevation = CardDefaults.cardElevation(0.dp),
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (!unlocked || hidden)
                    Modifier.border(
                        2.dp,
                        MaterialTheme.colorScheme.surface,
                        RoundedCornerShape(0.dp)
                    )
                else Modifier
            ),
        shape = RoundedCornerShape(0.dp),
        colors = CardColors(
            contentColor = MaterialTheme.colorScheme.onSurface,
            containerColor =
               if (unlocked) MaterialTheme.colorScheme.surface
               else MaterialTheme.colorScheme.background,
            disabledContainerColor = MaterialTheme.colorScheme.surface,
            disabledContentColor = MaterialTheme.colorScheme.onSurface
        ),
        onClick = {
            showBottomSheet = true
        }
    ) {

        Row(
            modifier = Modifier
                .padding(vertical = 10.dp)
                .padding(start = 10.dp, end = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(75.dp)
                    .background(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
            ){

                if (unlocked || !hidden) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(urlImage)
                            .crossfade(true)
                            .build(),
                        //colorFilter = if (!unlocked) ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation( 0f ) }) else null,
                        contentScale = ContentScale.Fit,
                        contentDescription = null,
                        modifier = Modifier.size(75.dp)
                    )
                }

                Icon(
                    painter = painterResource(id =
                        if (hidden == true && unlocked == false)
                            R.drawable.ic_quest
                        else if (unlocked == false)
                            R.drawable.ic_lock
                        else
                            // Placeholder for unlocked, will be transparent
                            R.drawable.ic_home
                    ),
                    contentDescription =
                        if (hidden) "Hidden Icon"
                        else if (!unlocked) "Locked Icon"
                        else "Unlocked Icon",
                    tint =
                        if (hidden == true && unlocked == false)
                            MaterialTheme.colorScheme.primary
                        else if (unlocked == false)
                            Color.White.copy(alpha = 0.9f)
                        else
                            Color.White.copy(alpha = 0f),
                    modifier = Modifier
                        .size(30.dp)
                        .then(
                            if (!unlocked && !hidden) Modifier.shadow(5.dp, ambientColor = Color.Black, spotColor = Color.Black, shape = RoundedCornerShape(5.dp))
                            else Modifier
                        ),
                )

            }

            Column(
                modifier = Modifier
                    .padding(start = 20.dp, end = 10.dp)
            ) {
                Text(
                    text =
                        if (unlocked || !hidden) name
                        else "Conquista Oculta",
                    style = MaterialTheme.typography.titleMedium,
                    color =
                        if (!unlocked) MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                        else MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .padding(bottom = 5.dp)
                )

                Text(
                    text =
                        if (unlocked || !hidden) description
                        else "Detalhes ocultos, clique para exibir spoiler da conquista",
                    maxLines = 2,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                    lineHeight = 19.sp,
                )
            }
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.surface,
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 10.dp, horizontal = 16.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                ) {

                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(urlImage)
                            .crossfade(true)
                            .build(),
                        //placeholder = painterResource(R.drawable.placeholder),
                        //error = painterResource(R.drawable.placeholder),
                        contentScale = ContentScale.Fit,
                        contentDescription = null,
                        modifier = Modifier.size(75.dp)
                    )

                    Column(
                        modifier = Modifier
                            .padding(start = 20.dp)
                    ) {
                        Text(
                            text = name,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier
                                .padding(bottom = 5.dp)
                        )

                        Text(
                            text = description,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                            lineHeight = 19.sp,
                        )
                    }
                }

                Row(
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                ) {
                    Text(
                        "Porcentagem global dos jogadores:",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                        lineHeight = 19.sp,
                    )

                    Text(
                        playerPercentUnlocked,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        color =
                            if (playerPercentUnlockedToFloat < 0.10f) MaterialTheme.colorScheme.tertiary
                            else MaterialTheme.colorScheme.primary,
                        lineHeight = 19.sp,
                    )
                }


                LinearProgressIndicator(
                    progress = playerPercentUnlockedToFloat,
                    trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                    color =
                        if (playerPercentUnlockedToFloat < 0.10f) MaterialTheme.colorScheme.tertiary
                        else MaterialTheme.colorScheme.onSecondary,
                    strokeCap = StrokeCap.Round,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(9.dp)
                )
            }

            Spacer(modifier = Modifier.padding(bottom = 200.dp))
        }
    }
}
