package com.app.gameshelf.data.model

import com.google.gson.annotations.SerializedName

data class GameResponse(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("data")
    val data: GameDataApi
)

data class GameDataApi(
    @SerializedName("type")
    val type: String,

    @SerializedName("cover")
    val cover: String,

    @SerializedName("background")
    val background: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("developers")
    val developers: List<String>,

    @SerializedName("publishers")
    val publishers: List<String>,

    @SerializedName("releaseDate")
    val releaseDate: ReleaseDate,

    @SerializedName("categories")
    val categories: List<Category>,

    @SerializedName("genres")
    val genres: List<Genre>,

    @SerializedName("dlcs")
    val dlcs: List<Int>,

    @SerializedName("achievementHighlights")
    val achievementsHighlights: AchievementsHighlights,

    @SerializedName("platforms")
    val platforms: Platforms,

    @SerializedName("screenshots")
    val screenshots: List<Screenshot>,

    @SerializedName("steamRatings")
    val steamRatings: SteamRatings,

    @SerializedName("gameRatings")
    val gameRatings: GameRatings,

    @SerializedName("reviews")
    val reviews: List<GameReview>,

    @SerializedName("languages")
    val languages: String,

    @SerializedName("userGameLog")
    val userGameLog: String?,

    @SerializedName("playingCount")
    val playingCount: Int,

    @SerializedName("reviewsCount")
    val reviewsCount: Int,

    @SerializedName("gameListsCount")
    val gameListsCount: Int
)

data class ReleaseDate(
    @SerializedName("coming_soon")
    val comingSoon: Boolean,

    @SerializedName("date")
    val date: String
)

data class Category(
    @SerializedName("id")
    val id: Int,

    @SerializedName("description")
    val description: String
)

data class Genre(
    @SerializedName("id")
    val id: String,

    @SerializedName("description")
    val description: String
)

data class AchievementsHighlights(
    @SerializedName("achieved")
    val achieved: Int,

    @SerializedName("max")
    val max: Int,

    @SerializedName("progress")
    val progress: Float,

    @SerializedName("lastUnlocked")
    val lastUnlocked: lastUnlocked?,

    @SerializedName("lastFive")
    val lastFive: List<lastFive>?
) {
    fun getProgressPercentage(): Float {
        return progress / 100
    }
}

data class lastUnlocked(
    @SerializedName("img")
    val img: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("desc")
    val description: String,

    @SerializedName("unlocktime")
    val unlocktime: Int,
)

data class lastFive(
    @SerializedName("img")
    val img: String,
)

data class Platforms(
    @SerializedName("windows")
    val windows: Boolean,

    @SerializedName("mac")
    val mac: Boolean,

    @SerializedName("linux")
    val linux: Boolean
)

data class Screenshot(
    @SerializedName("id")
    val id: Int,

    @SerializedName("path_thumbnail")
    val pathThumbnail: String,

    @SerializedName("path_full")
    val pathFull: String
)

data class SteamRatings(
    @SerializedName("reviewScore0to5")
    val reviewScore: Double,

    @SerializedName("reviewScoreDesc")
    val reviewScoreDesc: String,

    @SerializedName("totalReviews")
    val totalReviews: Int,

    @SerializedName("totalPositive")
    val totalPositive: Int,

    @SerializedName("totalNegative")
    val totalNegative: Int
) {

    fun getPositivePercentage(): Int {
        return if (totalReviews > 0) {
            ((totalPositive.toDouble() / totalReviews) * 100).toInt()
        } else {
            0
        }
    }
}

data class GameRatings(
    @SerializedName("total")
    val total: Int,

    @SerializedName("average")
    val average: Float,

    @SerializedName("ratings")
    val ratings: Ratings
)

data class Ratings(
    @SerializedName("1")
    val star1: Int,

    @SerializedName("2")
    val star2: Int,

    @SerializedName("3")
    val star3: Int,

    @SerializedName("4")
    val star4: Int,

    @SerializedName("5")
    val star5: Int
)

data class GameReview(
    @SerializedName("appid") val appId: String,
    @SerializedName("gameName") val gameName: String,
    @SerializedName("profilePicture") val profilePicture: String,
    @SerializedName("profileName") val profileName: String,
    @SerializedName("log") val log: String?,
    @SerializedName("hoursPlayed") val hoursPlayed: String,
    @SerializedName("reviewScore") val reviewScore: Int,
    @SerializedName("reviewComment") val reviewComment: String,
    @SerializedName("reviewDate") val reviewDate: String,
    @SerializedName("liked") val liked: Boolean
)

data class GameReviewsResponse(
    val reviews: List<GameReview>
)
