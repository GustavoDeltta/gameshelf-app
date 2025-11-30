package com.app.gameshelf.data.api

import com.app.gameshelf.data.model.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("api/game/{gameId}/achievements/{playerId}")
    suspend fun getAchievements(
        @Path("gameId") gameId: String,
        @Path("playerId") playerId: String,
        @Query("language") language: String = "brazilian"
    ): Response<ApiResponse>
}