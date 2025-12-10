package com.app.gameshelf.data.repository

import android.content.Context
import com.app.gameshelf.data.api.RetrofitClient
import com.app.gameshelf.data.model.Achievement
import com.app.gameshelf.data.model.GameDataApi
import com.app.gameshelf.utils.PreferencesManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GameRepository(private val context: Context) {

    private val apiService = RetrofitClient.apiService
    private val prefsManager = PreferencesManager(context)

    private fun getApiLanguage(): String {
        return prefsManager.getApiLanguage()
    }

    suspend fun getAchievements(
        gameId: String,
        playerId: String,
        language: String? = null
    ): Result<List<Achievement>> {
        return withContext(Dispatchers.IO) {
            try {
                val finalLanguage = language ?: getApiLanguage()

                val response = apiService.getAchievements(gameId, playerId, finalLanguage)

                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        Result.success(body.data.achievements)
                    } else {
                        Result.failure(Exception("Empty response or API error"))
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
        language: String? = null
    ): Result<GameDataApi> {
        return withContext(Dispatchers.IO) {
            try {
                val finalLanguage = language ?: getApiLanguage()

                val response = apiService.getGameDetails(gameId, finalLanguage)

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