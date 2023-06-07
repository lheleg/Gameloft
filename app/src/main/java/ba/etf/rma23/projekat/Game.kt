package ba.etf.rma23.projekat

data class Game(
    val id : Int?=0,
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
    val userImpressions: List<UserImpression>?
)

