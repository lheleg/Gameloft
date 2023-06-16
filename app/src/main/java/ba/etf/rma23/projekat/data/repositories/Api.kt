package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository.getHash
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*


interface Api {
    @Headers("Client-ID: vtfllttx3gwlzv55oajmcrbsswxpl0", "Authorization: Bearer ffhb27f1jylitms082osnbkxwvvkfs", "Content_Type: application/json")

    @GET("games/")
    suspend fun getGamesByName(
        @Query("search") name : String,
        @Query("fields") fields : String = "id,name,platforms.name,release_dates.human,rating,cover.url,genres.name,summary,age_ratings.rating,age_ratings.category;"
    ): Response<List<GetGameResponse>>

    @POST("games/")
    suspend fun getGamesById(
        @Header("Client-ID") client_id : String,
        @Header("Authorization") autho : String,
        @Body body : RequestBody
    ): Response<List<GetGameResponse>>

    @Headers("Content-Type: application/json")

    @GET("account/{aid}/games")
    suspend fun getSavedGames(
        @Path("aid") aid: String = getHash()
    ): Response<List<GetSwaggerResponse>>

    @POST("account/{aid}/game")
    suspend fun saveGame(
        @Body body : RequestBody,
        @Path("aid") aid: String = getHash()
    ): Response<GetSwaggerResponse>

    @DELETE("account/{aid}/game/{gid}/")
    suspend fun removeGame(
        @Path("gid") gid: Int,
        @Path("aid") aid: String = getHash()
    ): Response<GetRemoveResponse>

    @Headers("Content-Type: application/json")
    @POST("account/{aid}/game/{gid}/gamereview")
    suspend fun sendReview(
        @Body body : RequestBody,
        @Path("gid") gid: Int,
        @Path("aid") aid: String = getHash()
    ): Response<GameReview>

    @GET("/game/{gid}/gamereviews")
    suspend fun getReviewsForGame(
        @Path("gid") gid: Int
    ): Response<List<GameReview>>
}