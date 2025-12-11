package com.app.gameshelf.ui.screens.game

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.app.gameshelf.ui.components.backButton.BackButton
import com.app.gameshelf.R
import com.app.gameshelf.data.model.SteamRatings
import com.app.gameshelf.ui.components.buttonAddTo.buttonAddTo
import com.app.gameshelf.ui.components.highLightAchievements.HighLightAchievements
import com.app.gameshelf.ui.components.ratingCard.RatingCard
import com.app.gameshelf.ui.screens.gameDetails.GameDetailsUiState
import com.app.gameshelf.ui.screens.gameDetails.GameDetailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    gameId: String,
    navController: NavController,
    onBackClick: () -> Unit,
    onGameDetailsClick: (String, String) -> Unit,
    detailsViewModel: GameDetailsViewModel = viewModel()
){
    val uiState by detailsViewModel.uiState.collectAsState()
    val scrollVertical = rememberScrollState()

    LaunchedEffect(gameId) {
        detailsViewModel.loadGameDetails(gameId)
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollVertical),
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(290.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .blur(2.dp)
                    .height(290.dp),
                model = "https://cdn.cloudflare.steamstatic.com/steam/apps/${gameId}/header.jpg",
                contentDescription = "Game Banner",
                contentScale = ContentScale.Crop,
                alignment = androidx.compose.ui.BiasAlignment(
                    horizontalBias = -0.5f,
                    verticalBias = 0f
                )
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(290.dp)
                    .blur(10.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.background.copy(alpha = 0.1f),
                                MaterialTheme.colorScheme.background.copy(alpha = 0.3f),
                                MaterialTheme.colorScheme.background.copy(alpha = 1f)
                            )
                        )
                    )
            )

            BackButton(onBackClick = onBackClick)

            Column(
                modifier = Modifier
                    .padding(top = 120.dp)
                    .padding(horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .aspectRatio(2f / 3f),
                        shape = RoundedCornerShape(0.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        AsyncImage(
                            model = "https://cdn.cloudflare.steamstatic.com/steam/apps/${gameId}/library_600x900.jpg",
                            contentDescription = stringResource(R.string.game_cover),
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 16.dp),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        if(uiState.isLoading){
                            SkeletonLoading(with = 0.5f, modifier = Modifier.padding(bottom = 5.dp))
                            SkeletonLoading(with = 0.35f, height = 15.dp)
                            Spacer(modifier = Modifier.height(10.dp))
                            repeat(4){
                                SkeletonLoading(height = 15.dp, modifier = Modifier.padding(bottom = 5.dp))
                            }
                        } else {
                            Text(
                                text = uiState.gameData?.name ?: "",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.primary
                            )

                            Text(
                                uiState.gameData?.releaseDate?.date ?: "",
                                style = MaterialTheme.typography.titleMedium,
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = uiState.gameData?.description ?: "",
                                maxLines = 4,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                                textAlign = TextAlign.Justify,
                            )
                        }
                    }
                }
            }
        }

        //  Statistics of list, Players and Rating ---------------------------------

        Column(
            Modifier
                .fillMaxWidth()
                .padding(top = 5.dp)
                .padding(horizontal = 16.dp)
        ) {
            // Button backlog
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .height(60.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = MaterialTheme.shapes.medium
                        ),
                    contentAlignment = Alignment.Center
                ){
                    SkeletonLoading(with = 0.5f)
                }
            } else {
                buttonAddTo("completed")
            }


            // Developers and Publishers
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = MaterialTheme.shapes.medium
                    ),
                    horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    Modifier
                        .padding(16.dp)
                        .weight(0.5f)
                ) {
                    Text(
                        stringResource(R.string.developers),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                    )

                    if(uiState.isLoading){
                        SkeletonLoading(with = 0.5f)
                    }else {
                        Text(
                            text = uiState.gameData?.developers?.get(0) ?: "",
                            style = MaterialTheme.typography.titleMedium,
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(0.5f),
                    horizontalAlignment = androidx.compose.ui.Alignment.End
                ) {
                    Text(
                        stringResource(R.string.publishers),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                    )

                    if(uiState.isLoading){
                        SkeletonLoading(with = 0.8f)
                    }else{
                        Text(
                            text = uiState.gameData?.publishers?.get(0) ?: "",
                            style = MaterialTheme.typography.titleMedium,
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.End
                        )
                    }
                }
            }

            // Statistics of list, evaluation and players
            data class InfoItem(
                val quant: String,
                val category: String,
                val Description: String,
                val iconRes: Int,
                val color: Color
            )

            val infoList = listOf(
                InfoItem(
                    quant = uiState.gameData?.gameListsCount.toString(),
                    category = stringResource(R.string.list),
                    Description = stringResource(R.string.listDescription),
                    iconRes = R.drawable.ic_list,
                    color = MaterialTheme.colorScheme.secondary
                ),
                InfoItem(
                    quant = uiState.gameData?.reviewsCount.toString(),
                    category = stringResource(R.string.reviews),
                    Description = stringResource(R.string.reviewsDescription),
                    iconRes = R.drawable.ic_reviews,
                    color = MaterialTheme.colorScheme.onPrimary
                ),
                InfoItem(
                    quant = uiState.gameData?.playingCount.toString(),
                    category = stringResource(R.string.players),
                    Description = stringResource(R.string.playersDescription),
                    iconRes = R.drawable.ic_control,
                    color = MaterialTheme.colorScheme.tertiary
                )
            )

            Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                infoList.forEach { info ->
                    Column(
                        modifier = Modifier
                            .height(80.dp)
                            .clickable {
                                onGameDetailsClick(
                                    info.category,
                                    uiState.gameData?.name ?: ""
                                )
                            }
                            .background(
                                color = MaterialTheme.colorScheme.surface,
                                shape = MaterialTheme.shapes.medium
                            )
                            .weight(1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
                    ) {
                        if(uiState.isLoading){
                            SkeletonLoading(with = 0.5f)
                        } else {
                            Text(
                                info.quant,
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 20.sp
                            )
                        }

                        Row(
                            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = androidx.compose.ui.res.painterResource(info.iconRes),
                                contentDescription = info.Description,
                                tint = info.color,
                                modifier = Modifier
                                    .size(20.dp)
                                    .padding(end = 3.dp)
                            )

                            Text(
                                info.category,
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 15.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    stringResource(R.string.achievements),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium,
                )

                if(uiState.isLoading){
                    SkeletonLoading(with = 0.5f)
                } else {
                    Text(
                        text =
                            if (uiState.gameData?.achievementsHighlights?.max == 0) ""
                            else stringResource(R.string.seeAll),
                        style = MaterialTheme.typography.labelMedium,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                        modifier = Modifier.clickable {
                            if (uiState.gameData?.achievementsHighlights?.max == 0)
                                return@clickable

                            onGameDetailsClick(
                                "achievements",
                                uiState.gameData?.name ?: ""
                            )
                        }
                    )
                }
            }

            //  Achievements --------------------------------

            if (uiState.isLoading){
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .background(
                            MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
                            shape = MaterialTheme.shapes.small
                        )
                )
            }

            else {
                HighLightAchievements(
                    onClickListener = {
                        if (uiState.gameData?.achievementsHighlights?.max == 0)
                            return@HighLightAchievements

                        onGameDetailsClick(
                            "achievements",
                            uiState.gameData?.name ?: ""
                        )
                    },
                    gameID = gameId,
                    unLocked = uiState.gameData?.achievementsHighlights?.achieved ?: 0,
                    locked = uiState.gameData?.achievementsHighlights?.max ?: 0,
                    progress = uiState.gameData?.achievementsHighlights?.getProgressPercentage() ?: 0f,
                    lastUnlockedImage = uiState.gameData?.achievementsHighlights?.lastUnlocked?.img ?: "",
                    lastUnlockedName = uiState.gameData?.achievementsHighlights?.lastUnlocked?.name ?: "",
                    lastUnlockedDescription = uiState.gameData?.achievementsHighlights?.lastUnlocked?.description ?: "",
                    listOfLastUnlocked = uiState.gameData?.achievementsHighlights?.lastFive ?: emptyList()
                )
            }

            // --------------------------------
            //  Rating
            // --------------------------------

            var selectedTab by remember { mutableStateOf(0) }
            val tabs = listOf("Steam", "Game Shelf")

            Column(
                modifier = Modifier.fillMaxSize().padding(top = 10.dp)
            ) {
                TabRow(
                    selectedTabIndex = selectedTab,
                    modifier = Modifier.fillMaxWidth(),
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.primary
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = {
                                Text(
                                    text = title,
                                    fontWeight = if (selectedTab == index) FontWeight.Medium else FontWeight.Normal,
                                    color = if (selectedTab == index) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                                    fontSize = 14.sp,
                                    style = MaterialTheme.typography.labelMedium,
                                )
                            }
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 16.dp)
                ) {
                    when (selectedTab) {
                        0 -> SteamRatings( uiState = uiState)
                        1 -> RatingCard(
                            reviewScore0to5 = 4.3f,
                            progressStar5 = 0.8f,
                            progressStar4 = 0.6f,
                            progressStar3 = 0.4f,
                            progressStar2 = 0.2f,
                            progressStar1 = 0.1f
                        )
                    }
                }
            }

            HorizontalDivider(thickness = 2.dp, modifier = Modifier.padding(vertical = 10.dp))

            // --------------------------------
            //  Images carousel
            // --------------------------------

            val screenshots = uiState.gameData?.screenshots ?: emptyList()
            if (screenshots.isEmpty()) return

            val carouselState = rememberCarouselState { screenshots.size }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                if (uiState.isLoading){
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(Color.Gray.copy(alpha = 0.5f))
                    )
                }

                HorizontalMultiBrowseCarousel(
                    state = carouselState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    preferredItemWidth = 300.dp,
                    itemSpacing = 8.dp
                ) { index ->
                    val screenshot = screenshots[index]

                    Card(
                        modifier = Modifier
                            .height(200.dp)
                            .maskClip(ShapeDefaults.Small),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    ) {
                        AsyncImage(
                            model = screenshot.pathThumbnail,
                            contentDescription = "Screenshot ${index + 1}",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }


        }
    }
}

@Composable
fun SteamRatings(
    uiState: GameDetailsUiState
){
    val reviewScore = uiState.gameData?.steamRatings?.reviewScore ?: 0.0

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

            if(uiState.isLoading){
                SkeletonLoading(with = 0.5f, height = 36.dp)
            } else {
                Text(
                    text = reviewScore.toString(),
                    color = color,
                    style = MaterialTheme.typography.labelMedium,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }

        VerticalDivider( thickness = 2.dp)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            if (uiState.isLoading){
                SkeletonLoading(with = 0.5f, height = 15.dp)
            } else {
                Text(
                    text = uiState.gameData?.steamRatings?.reviewScoreDesc ?: "",
                    color = color,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Row(){
                Text(
                    text = stringResource(R.string.steamReview) + " ",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                )

                if(uiState.isLoading){
                    SkeletonLoading(with = 0.5f, height = 15.dp)
                } else {
                    Text(
                        text = uiState.gameData?.steamRatings?.totalReviews.toString(),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }

            Row(){
                Text(
                    text = stringResource(R.string.steamPositive) + " ",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                )

                if(uiState.isLoading){
                    SkeletonLoading(with = 0.5f, height = 15.dp)
                } else {
                    Text(
                        text = uiState.gameData?.steamRatings?.totalPositive.toString(),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }

            Row(){
                Text(
                    text = stringResource(R.string.steamNegative) + " ",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                )

                if (uiState.isLoading){
                    SkeletonLoading(with = 0.5f, height = 15.dp)
                } else {
                    Text(
                        text = uiState.gameData?.steamRatings?.totalNegative.toString(),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}

@Composable
fun SkeletonLoading(
    modifier: Modifier = Modifier,
    with: Float = 1f,
    height: Dp = 25.dp
){
    val infiniteTransition = rememberInfiniteTransition()
    val localConfig = LocalConfiguration.current
    val target = (localConfig.screenWidthDp * 3).toFloat()

    val scale by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = target,
        animationSpec =
            infiniteRepeatable(
                animation = tween(1000),
                repeatMode = RepeatMode.Restart,
            ), label = "shimmer"
    )

    val skeletonColor = Brush.linearGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primary.copy(alpha = 0f),
            MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
            MaterialTheme.colorScheme.primary.copy(alpha = 0f)
        ),
        end = Offset(x = scale, y = 0f)
    )

    Box(
        modifier = modifier
            .fillMaxWidth(with)
            .background(
                skeletonColor,
                shape = MaterialTheme.shapes.small
            )
            .height(height)
    )
}
