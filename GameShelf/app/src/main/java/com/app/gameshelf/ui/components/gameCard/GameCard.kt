package com.app.gameshelf.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.app.gameshelf.R

@Composable
fun GameCard(
    gameId: String,
    onGameClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val imageUrl = remember(gameId) {
        "https://cdn.cloudflare.steamstatic.com/steam/apps/$gameId/library_600x900.jpg"
    }

    val request = remember(imageUrl) {
        ImageRequest.Builder(context)
            .data(imageUrl)
            .crossfade(300)
            .memoryCacheKey(imageUrl)
            .diskCacheKey(imageUrl)
            .build()
    }

    Card(
        modifier = modifier
            .aspectRatio(2f / 3f)
            .clickable { onGameClick(gameId) },
        shape = RoundedCornerShape(8.dp),
        //elevation = CardDefaults.cardElevation(4.dp)
    ) {
        AsyncImage(
            model = request,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            //placeholder = androidx.compose.ui.res.painterResource(R.drawable.placeholder),
            //error = androidx.compose.ui.res.painterResource(R.drawable.placeholder)
        )
    }
}
