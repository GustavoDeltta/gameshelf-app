package com.app.gameshelf.data.model

data class ReviewRequest(
    val rating: Double,
    val text: String,
    val liked: Boolean,
    val containsSpoilers: Boolean
)
