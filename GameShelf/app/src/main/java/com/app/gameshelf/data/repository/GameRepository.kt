package com.app.gameshelf.data.repository

import android.content.Context
import android.util.Log
import com.app.gameshelf.data.api.RetrofitClient
import com.app.gameshelf.data.model.Achievement
import com.app.gameshelf.data.model.GameDataApi
import com.app.gameshelf.data.model.GameLogRequest
import com.app.gameshelf.data.model.GameLogResponse
import com.app.gameshelf.data.model.GameReview
import com.app.gameshelf.data.model.ReviewRequest
import com.app.gameshelf.utils.PreferencesManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GameRepository(private val context: Context) {

    private val apiService = RetrofitClient.apiService
    private val prefsManager = PreferencesManager(context)

    private fun getApiLanguage(): String {
        return prefsManager.getApiLanguage()
    }

    private fun getAuthToken(): String? {
        return prefsManager.getAuthToken()
    }

    suspend fun createGameLog(gameId: Int, status: String): Result<GameLogResponse> {
        return withContext(Dispatchers.IO) {
            try {
                Log.d("GameRepository", "createGameLog: gameId=$gameId, status=$status")
                val token = getAuthToken()
                if (token != null) {
                    val request = GameLogRequest(gameId, status)
                    val response = apiService.createGameLog("Bearer $token", request)
                    Log.d("GameRepository", "createGameLog response code: ${response.code()}")
                    if (response.isSuccessful) {
                        val body = response.body()
                        Log.d("GameRepository", "createGameLog body: $body")
                        if (body != null) {
                            Result.success(body)
                        } else {
                            Result.failure(Exception("Empty response or API error"))
                        }
                    } else {
                        Log.e("GameRepository", "createGameLog error body: ${response.errorBody()?.string()}")
                        Result.failure(Exception("Error: ${response.code()}"))
                    }
                } else {
                    Log.e("GameRepository", "createGameLog: Token not found")
                    Result.failure(Exception("Token not found"))
                }
            } catch (e: Exception) {
                Log.e("GameRepository", "createGameLog exception", e)
                Result.failure(e)
            }
        }
    }

    suspend fun createReview(gameId: String, rating: Double, text: String, liked: Boolean, containsSpoilers: Boolean = false): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val token = getAuthToken()
                if (token != null) {
                    val request = ReviewRequest(rating, text, liked, containsSpoilers)
                    val response = apiService.createReview("Bearer $token", gameId, request)
                    
                    if (response.isSuccessful) {
                        Result.success(Unit)
                    } else {
                        Result.failure(Exception("Error: ${response.code()}"))
                    }
                } else {
                    Result.failure(Exception("Token not found"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
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
        steamId: String,
        language: String? = null
    ): Result<GameDataApi> {
        return withContext(Dispatchers.IO) {
            try {
                val finalLanguage = language ?: getApiLanguage()
                Log.d("GameRepository", "getGameDetails: gameId=$gameId, steamId=$steamId, language=$finalLanguage")

                val response = apiService.getGameDetails(gameId, finalLanguage, steamId)
                Log.d("GameRepository", "getGameDetails response code: ${response.code()}")

                if (response.isSuccessful) {
                    val body = response.body()
                    Log.d("GameRepository", "getGameDetails body success=${body?.success}, data=${body?.data}")
                    if (body != null && body.success) {
                        // Log fields to check for nulls
                        val data = body.data
                        Log.d("GameRepository", "getGameDetails data: name=${data.name}, desc=${data.description?.take(20)}, devs=${data.developers}, pubs=${data.publishers}")
                        Result.success(body.data)
                    } else {
                        Log.e("GameRepository", "getGameDetails body null or success=false")
                        Result.failure(Exception("Empty response or API error"))
                    }
                } else {
                    Log.e("GameRepository", "getGameDetails error: ${response.code()}")
                    Result.failure(Exception("Error: ${response.code()}"))
                }
            } catch (e: Exception) {
                Log.e("GameRepository", "getGameDetails exception", e)
                Result.failure(e)
            }
        }
    }

    suspend fun getGameReviews(gameId: String): Result<List<GameReview>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getGameReviews(gameId)

                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        Result.success(body.reviews)
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