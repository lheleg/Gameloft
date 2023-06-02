package ba.etf.rma23.projekat

import com.google.gson.annotations.SerializedName

data class Game(
    var title: String?,
    var platform: String?,
    var releaseDate: String?,
    var rating: Double?,
    var coverImage: String?,
    var esrbRating: String?,
    var developer: String?,
    var publisher: String?,
    var genre: String?,
    var description: String?,
    val userImpressions: List<UserImpression>?,
    val id : Int?=0)

