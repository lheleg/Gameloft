package com.example.videogames

data class UserRating(
    override val username: String,
    override val timestamp: Long,
    val rating: Double
):UserImpression()

