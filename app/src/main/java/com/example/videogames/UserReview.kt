package com.example.videogames

data class UserReview(
    override val username: String,
    override val timestamp: Long,
    val review: String
):UserImpression()

