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

    @SerializedName("languages")
    val languages: String
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
    val lastUnlocked: lastUnlocked,

    @SerializedName("lastFive")
    val lastFive: List<lastFive>
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