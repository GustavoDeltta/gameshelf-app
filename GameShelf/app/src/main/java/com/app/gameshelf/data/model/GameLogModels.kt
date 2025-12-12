package com.app.gameshelf.data.model

data class GameLogRequest(
    val gameId: Int,
    val status: String
)

data class GameLogResponse(
    val id: String,
    val userId: String,
    val gameId: Int,
    val status: String,
    val createdAt: String
)

data class UpdateGameLogStatusRequest(
    val status: String
)

data class UpdateGameLogStatusResponse(
    val message: String
)

data class DeleteGameLogResponse(
    val message: String
)
