package com.app.gameshelf.ui.components.logBottomSheet

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.gameshelf.R
import com.app.gameshelf.data.repository.GameRepository
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun logBottomSheet(
    steamId: String,
    gameID: String,
    onDismiss: () -> Unit,
    onStatusUpdate: (String) -> Unit, // Callback para atualização local
    onReviewClick: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val gameRepository = remember { GameRepository(context) }

    fun handleStatusClick(status: String) {
        scope.launch {
            try {
                // Tenta converter gameID para Int; caso falhe, usa 0 ou trata o erro
                val idInt = gameID.toIntOrNull() ?: 0
                val result = gameRepository.createGameLog(idInt, status)
                if (result.isSuccess) {
                    Toast.makeText(context, "Game added to $status", Toast.LENGTH_SHORT).show()
                    onStatusUpdate(status) // Notifica a atualização local
                    onDismiss()
                } else {
                    val exception = result.exceptionOrNull()
                    val errorMessage = if (exception?.message?.contains("401") == true) {
                        "Session expired. Please login again."
                    } else {
                        "Failed to add game log"
                    }
                    Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedButton(
                onClick = { handleStatusClick("Backlog") }, // Usando "Backlog" conforme esperado pelo backend
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_add),
                        contentDescription = "Add to button",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(30.dp),
                    )
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            "Add to",
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            "For stay without category.",
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.labelLarge,
                            fontSize = 13.sp
                        )
                    }
                }
            }
            OutlinedButton(
                onClick = { handleStatusClick("Finished") }, // "Finished" ao invés de "completed"
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSecondary),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.3f)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_check),
                        contentDescription = "Finished icon",
                        tint = MaterialTheme.colorScheme.onSecondary,
                        modifier = Modifier
                            .size(30.dp),
                    )
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            "Finished",
                            color = MaterialTheme.colorScheme.onSecondary,
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            "For completed games.",
                            color = MaterialTheme.colorScheme.onSecondary,
                            style = MaterialTheme.typography.labelLarge,
                            fontSize = 13.sp
                        )
                    }
                }
            }
            OutlinedButton(
                onClick = { handleStatusClick("Playing") }, // "Playing" com P maiúsculo
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.onPrimary),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.3f)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_control),
                        contentDescription = "PLaying icon",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .size(30.dp),
                    )
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            "Playing",
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            "For playing games.",
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.labelLarge,
                            fontSize = 13.sp
                        )
                    }
                }
            }
            OutlinedButton(
                onClick = { handleStatusClick("Backlog") }, // "Backlog"
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.3f)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_list),
                        contentDescription = "Backlog icon",
                        tint = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier
                            .size(30.dp),
                    )
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            "Backlog",
                            color = MaterialTheme.colorScheme.tertiary,
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            "For games to be played.",
                            color = MaterialTheme.colorScheme.tertiary,
                            style = MaterialTheme.typography.labelLarge,
                            fontSize = 13.sp
                        )
                    }
                }
            }
            OutlinedButton(
                onClick = { handleStatusClick("Dropped") }, // "Dropped" com D maiúsculo
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_dropped),
                        contentDescription = "Finished icon",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier
                            .size(30.dp),
                    )
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            "Dropped",
                            color = MaterialTheme.colorScheme.secondary,
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            "For games that were dropped.",
                            color = MaterialTheme.colorScheme.secondary,
                            style = MaterialTheme.typography.labelLarge,
                            fontSize = 13.sp
                        )
                    }
                }
            }
            HorizontalDivider(modifier = Modifier.padding(10.dp), thickness = 2.dp, color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = { },
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .height(60.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_list),
                            contentDescription = "Finished icon",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .size(30.dp)
                        )
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                "List",
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                "Add this game on a collection.",
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                                style = MaterialTheme.typography.labelLarge,
                                fontSize = 13.sp
                            )
                        }
                        Icon(
                            painter = painterResource(R.drawable.ic_back),
                            contentDescription = "Finished icon",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .size(20.dp)
                                .rotate(180f)
                        )
                    }
                }
                OutlinedButton(
                    onClick = onReviewClick,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .height(60.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_reviews),
                            contentDescription = "Finished icon",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .size(30.dp)
                        )
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                "Review",
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                "What did you think of the game?",
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                                style = MaterialTheme.typography.labelLarge,
                                fontSize = 13.sp
                            )
                        }
                        Icon(
                            painter = painterResource(R.drawable.ic_back),
                            contentDescription = "Finished icon",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .size(20.dp)
                                .rotate(180f)
                        )
                    }
                }
            }
        }
    }
}
