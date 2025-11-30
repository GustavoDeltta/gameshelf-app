package com.app.gameshelf.data.repository

import com.app.gameshelf.data.api.RetrofitClient
import com.app.gameshelf.data.model.Achievement
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
}