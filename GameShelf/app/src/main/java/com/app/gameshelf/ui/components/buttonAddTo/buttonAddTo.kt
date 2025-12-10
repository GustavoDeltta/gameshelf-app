package com.app.gameshelf.ui.components.buttonAddTo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.app.gameshelf.R

@Composable
fun buttonAddTo(status: String) {
    data class StatusData(
        val text: String,
        val color: Color,
        val icon: Int,
        val textColor: Color
    )

    val statusData = when (status) {
        "backlog" -> StatusData(stringResource(R.string.list), MaterialTheme.colorScheme.tertiary, R.drawable.ic_list, MaterialTheme.colorScheme.surface)
        "playing" -> StatusData(stringResource(R.string.playing), MaterialTheme.colorScheme.onPrimary, R.drawable.ic_control, MaterialTheme.colorScheme.primary)
        "completed" -> StatusData(stringResource(R.string.completed), MaterialTheme.colorScheme.onSecondary, R.drawable.ic_reviews, MaterialTheme.colorScheme.primary)
        else -> StatusData(stringResource(R.string.addTo), MaterialTheme.colorScheme.primary, R.drawable.ic_add, MaterialTheme.colorScheme.surface)
    }

    Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(
            containerColor = statusData.color,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .height(60.dp)
            .background(
                color = statusData.color,
                shape = MaterialTheme.shapes.medium
            ),
    ) {
        Icon(
            painter = androidx.compose.ui.res.painterResource(statusData.icon),
            contentDescription = "${statusData.text}",
            tint = statusData.textColor,
            modifier = Modifier.padding(end = 8.dp),
        )

        Text(
            "${statusData.text}",
            style = MaterialTheme.typography.titleMedium,
            color = statusData.textColor
        )
    }
}