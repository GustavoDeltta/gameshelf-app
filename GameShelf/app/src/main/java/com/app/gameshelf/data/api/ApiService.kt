package com.app.gameshelf.data.api

import androidx.compose.ui.res.stringResource
import com.app.gameshelf.data.model.ApiResponse
import com.app.gameshelf.data.model.GameResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("api/game/{gameId}/achievements/{playerId}")
    suspend fun getAchievements(
        @Path("gameId") gameId: String,
        @Path("playerId") playerId: String,
        @Query("language") language: String
    ): Response<ApiResponse>

    // TODO: remover essas informações adicionais
    @GET("api/game/{gameId}")
    suspend fun getGameDetails(
        @Path("gameId") gameId: String,
        @Query("language") language: String,
        @Query("steamid") steamAppId: String = "76561199157114802"
    ): Response<GameResponse>

}