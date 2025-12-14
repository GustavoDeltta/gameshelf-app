package com.app.gameshelf.data.model

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val uid: String,
    val username: String,
    val steamId: String?,
    val ownedGames: List<Int>?,
    val role: String,
    val token: String,
    val refreshToken: String,
    val expiresIn: String
)

data class User(
    val uid: String,
    val email: String,
    val username: String,
    val steamId: String?,
    val steamProfilePicture: String?,
    val ownedGames: List<OwnedGame>?,
    val createdAt: String,
    val role: String
)

data class OwnedGame(
    val appid: Int,
    val name: String,
    val playtime_forever: Int,
    val playtime_2weeks: Int?,
    val last_played: Long
)

data class SignupRequest(
    val username: String,
    val email: String,
    val password: String,
)

data class SignupResponse(
    val message: String,
    val user: User
)

data class LinkSteamRequest(
    val steamId: String
)

data class LinkSteamResponse(
    val message: String,
    val user: User
)