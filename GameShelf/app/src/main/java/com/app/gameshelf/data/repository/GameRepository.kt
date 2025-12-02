package com.app.gameshelf.data.repository

import com.app.gameshelf.data.api.RetrofitClient
import com.app.gameshelf.data.model.Achievement
import com.app.gameshelf.data.model.AchievementData
import com.app.gameshelf.data.model.GameDataApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GameRepository {

    private val apiService = RetrofitClient.apiService

    suspend fun getAchievements(
        gameId: String,
        playerId: String,
        language: String = "brazilian"
    ): Result<List<Achievement>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getAchievements(gameId, playerId, language)

                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        Result.success(body.data.achievements)
                    } else {
                        Result.failure(Exception("Resposta vazia da API"))
                    }
                } else {
                    Result.failure(Exception("Erro na API: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun getGameDetails(
        gameId: String,
        language: String = "brazilian"
    ): Result<GameDataApi> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getGameDetails(gameId, language)

                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.success) {
                        Result.success(body.data)
                    } else {
                        Result.failure(Exception("Empty response or API error"))
                    }
                } else {
                    Result.failure(Exception("Error: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}