package ba.etf.rma23.projekat.data.repositories

import com.google.gson.annotations.SerializedName

data class GetGameResponse(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val title : String?,
    @SerializedName("platforms") val platforms: List<Platform>?,
    @SerializedName("rating") var rating: Double?,
    @SerializedName("release_dates") val release: List<ReleaseDate>?,
    @SerializedName("cover") val cover: Cover?,
    @SerializedName("summary") val summary: String?,
    @SerializedName("age_ratings") val ageRating: List<AgeRating>?,
    @SerializedName("genre") val genre: List<Genre>?,
    @SerializedName("involved_companies") val i_companies: List<ICompany>?,
)

data class Platform(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)

data class Cover(
    @SerializedName("id") val id: Int,
    @SerializedName("url") val coverUrl: String
)

data class ReleaseDate(
    @SerializedName("id") val id: Int,
    @SerializedName("human") val human: String
)

data class Genre(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)

data class AgeRating(
    @SerializedName("id") val id: Int,
    @SerializedName("category") val category: Int,
    @SerializedName("rating") val rating : Int
)

data class ICompany(
    @SerializedName("id") val id: Int,
    @SerializedName("company") val company: Company
)
data class Company(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)