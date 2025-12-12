package com.app.gameshelf.data.api

import androidx.compose.ui.res.stringResource
import com.app.gameshelf.data.model.ApiResponse
import com.app.gameshelf.data.model.GameResponse
import com.app.gameshelf.data.model.GameReviewsResponse
import com.app.gameshelf.data.model.LinkSteamRequest
import com.app.gameshelf.data.model.LinkSteamResponse
import com.app.gameshelf.data.model.LoginRequest
import com.app.gameshelf.data.model.LoginResponse
import com.app.gameshelf.data.model.SignupRequest
import com.app.gameshelf.data.model.SignupResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("api/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("api/register")
    suspend fun signup(@Body request: SignupRequest): Response<SignupResponse>

    @POST("api/link-steam")
    suspend fun linkSteam(
        @Header("Authorization") token: String,
        @Body request: LinkSteamRequest
    ): Response<LinkSteamResponse>

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
        @Query("steamid") steamId: String
    ): Response<GameResponse>

    @GET("api/reviews/game/{gameId}")
    suspend fun getGameReviews(
        @Path("gameId") gameId: String
    ): Response<GameReviewsResponse>

}