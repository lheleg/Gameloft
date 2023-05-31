package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game
import retrofit2.Response
import retrofit2.http.*


interface Api {
    @Headers("Client-ID: vtfllttx3gwlzv55oajmcrbsswxpl0", "Authorization: Bearer zosfvre7yavig38pvsevxl6rxtdsb9", "Content_Type: application/json")

    @GET("games/")
    suspend fun getGamesByName(
        @Query("search") name : String,
        @Query("fields") fields : String = "name,platforms.name,release_dates.human,rating,cover.url,genres.name,summary,age_ratings.category"
        ): Response<List<Game>>
}