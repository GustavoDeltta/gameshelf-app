package com.app.gameshelf.ui.components.backButton

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.gameshelf.R

@Composable
fun backButton (navigation: NavController){
    IconButton(
        modifier = Modifier
            .padding(top = 20.dp, start = 16.dp)
            .size(45.dp)
            .background(
                color = MaterialTheme.colorScheme.background.copy(alpha = 0.5f),
                shape = androidx.compose.foundation.shape.CircleShape
            ),
        onClick = {
            navigation.popBackStack()
        }
    ) {
        Icon(
            painter = androidx.compose.ui.res.painterResource(id = R.drawable.ic_back),
            contentDescription = "Voltar",
            tint = MaterialTheme.colorScheme.primary
        )
    }
}