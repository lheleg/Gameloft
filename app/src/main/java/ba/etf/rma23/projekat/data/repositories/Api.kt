package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository.getHash
import retrofit2.Response
import retrofit2.http.*


interface Api {
    @Headers("Client-ID: vtfllttx3gwlzv55oajmcrbsswxpl0", "Authorization: Bearer ffhb27f1jylitms082osnbkxwvvkfs", "Content_Type: application/json")

    @GET("games/")
    suspend fun getGamesByName(
        @Query("search") name : String,
        @Query("fields") fields : String = "id,name,platforms.name,release_dates.human,rating,cover.url,genres.name,summary,age_ratings.category",
        @Query("limit") limit : Int = 11
    ): Response<List<GetGameResponse>>

    @GET("games/{id}")
    suspend fun getGamesById(
        @Path("id") id: Int,
        @Query("fields") fields : String = "id,name,platforms.name,release_dates.human,rating,cover.url,genres.name,summary,age_ratings.category",
    ): Response<GetGameResponse>

    @Headers("Content-Type: application/json")

    @GET("account/{aid}/games")
    suspend fun getSavedGames(
        @Path("aid") aid: String = getHash()
    ): Response<List<GetSwaggerResponse>>

    @POST("account/{aid}/game")
    suspend fun saveGame(
        @Query("igdb_id") igdb : Int,
        @Query("name") name : String,
        @Path("aid") aid: String = getHash()
    ): Response<GetSwaggerResponse>
}