package com.app.gameshelf.data.model

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("data")
    val data: AchievementData
)

data class AchievementData(
    @SerializedName("totalAchievements")
    val totalAchievements: Int,

    @SerializedName("achieved")
    val achieved: Int,

    @SerializedName("notAchieved")
    val notAchieved: Int,

    @SerializedName("progress")
    val progress: Double,

    @SerializedName("achievements")
    val achievements: List<Achievement>
)

data class Achievement(
    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("icon")
    val icon: String,

    @SerializedName("hidden")
    val hidden: Boolean,

    @SerializedName("player_percent_unlocked")
    val playerPercentUnlocked: String,

    @SerializedName("achieved")
    val achieved: Boolean,
) {
    fun playerPercentUnlockedToFloat(): Float {
        return playerPercentUnlocked.toFloat() / 100
    }
}