package ba.etf.rma23.projekat

import com.google.gson.annotations.SerializedName

data class Game(
    val title: String?,
    val platform: String?,
    val releaseDate: String?,
    var rating: Double?,
    val coverImage: String?,
    val esrbRating: String?,
    val developer: String?,
    val publisher: String?,
    val genre: String?,
    val description: String?,
    val userImpressions: List<UserImpression>?,
    val id : Int?=0)

