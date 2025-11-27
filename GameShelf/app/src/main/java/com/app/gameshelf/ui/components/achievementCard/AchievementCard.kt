package com.app.gameshelf.ui.components.achievementCard

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.app.gameshelf.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AchievementCard(
    unLockedet: Boolean = false,
    urlImage: String,
    hidden: Boolean = false,
    name: String,
    description: String,
){

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(0.dp),
        colors = CardColors(
            contentColor = MaterialTheme.colorScheme.onSurface,
            containerColor = MaterialTheme.colorScheme.surface,
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
                if (!hidden) {
                    AsyncImage(
                        model = urlImage,
                        contentDescription = "Foto da conquista ${name}",
                        colorFilter =
                            if (!unLockedet) ColorFilter.colorMatrix(ColorMatrix().apply {
                                setToSaturation(
                                    0f
                                )
                            })
                            else null,
                        modifier = Modifier.size(75.dp),
                    )
                }

                Icon(
                    painter =
                        if (!unLockedet && !hidden) androidx.compose.ui.res.painterResource(id = R.drawable.ic_lock)
                        else if (hidden) androidx.compose.ui.res.painterResource(id = R.drawable.ic_quest)
                        else androidx.compose.ui.res.painterResource(id = R.drawable.ic_lock),
                    contentDescription =
                        if (!unLockedet && !hidden) "Lockedet Icon"
                        else if (hidden) "Hidden Icon"
                        else "imagem da conquista",
                    tint =
                        if (!unLockedet && !hidden) Color.White.copy(alpha = 0.8f)
                        else if (hidden) MaterialTheme.colorScheme.primary
                        else Color.White.copy(alpha = 0f),
                    modifier = Modifier
                        .size(30.dp)
                        .shadow(4.dp),
                )

            }

            Column(
                modifier = Modifier
                    .padding(start = 20.dp)
            ) {
                Text(
                    text =
                        if (!hidden) name
                        else "Conquista Oculta",
                    style = MaterialTheme.typography.titleMedium,
                    color =
                        if (!unLockedet) MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                        else MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .padding(bottom = 5.dp)
                )

                Text(
                    text =
                        if (!hidden) description
                        else "Detalhes ocultos, clique para exibir spoiler da conquista",
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
        ) {
            Row(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                AsyncImage(
                    model = urlImage,
                    contentDescription = "Foto da conquista ${name}",
                    modifier = Modifier.size(75.dp),
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

            Spacer(modifier = Modifier.padding(bottom = 200.dp))
        }
    }
}


