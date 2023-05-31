package ba.etf.rma23.projekat

import androidx.lifecycle.viewmodel.CreationExtras
import com.google.gson.annotations.SerializedName

data class Game(
    @SerializedName("name") val title: String?,
    @SerializedName("platforms.name") val platform: String?,
    @SerializedName("release_date") var releaseDate: String?,
    @SerializedName("rating") val rating: Double?,
    @SerializedName("cover.url") val coverImage: String?,
    @SerializedName("age_ratings.category") val esrbRating: String?,
    @SerializedName("developer") val developer: String?,
    @SerializedName("publisher") val publisher: String?,
    @SerializedName("genre.name") val genre: String?,
    @SerializedName("summary") val description: String?,
    @SerializedName("user_impressions") val userImpressions: List<UserImpression>?
)
