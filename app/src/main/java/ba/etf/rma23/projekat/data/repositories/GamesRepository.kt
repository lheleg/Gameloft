package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

object GamesRepository {

    suspend fun getGamesByName(search : String
    ) : List<Game>? {
        return withContext(Dispatchers.IO) {
            var response = IGDBApiConfig.retrofit.getGamesByName(search)
            val responseBody = response.body()
            var games = ArrayList<Game>()
            for(i in 0 until responseBody?.size!!){
                games?.add(Game(responseBody?.get(i)?.title,responseBody?.get(i)?.platforms?.get(0)?.name,responseBody?.get(i)?.release?.get(0)?.human, responseBody?.get(i)?.rating,responseBody?.get(i)?.cover?.coverUrl,"","","","", responseBody?.get(i)?.summary,
                    emptyList(), responseBody?.get(i)?.id
                ))
            }
            return@withContext games
        }
    }

    suspend fun getGameById(id : Int): Game?{
        return withContext(Dispatchers.IO){
            val body =
                "fields id,name,platforms.name,involved_companies.company.name,release_dates.human,rating,cover.url,genres.name,summary,age_ratings.category; where id = $id;"
            val response = IGDBApiConfig.retrofit.getGamesById("vtfllttx3gwlzv55oajmcrbsswxpl0","Bearer ffhb27f1jylitms082osnbkxwvvkfs", body.toRequestBody("text/plain".toMediaType()))
            val responseBody = response.body()
            val game = Game(responseBody?.get(0)?.title, responseBody?.get(0)?.platforms?.get(0)?.name,responseBody?.get(0)?.release?.get(0)?.human,responseBody?.get(0)?.rating,responseBody?.get(0)?.cover?.coverUrl,
                responseBody?.get(0)?.ageRating?.get(0)?.category.toString(),responseBody?.get(0)?.i_companies?.get(0)?.company?.name, responseBody?.get(0)?.i_companies?.get(0)?.company?.name,
                responseBody?.get(0)?.genre?.get(0)?.name,responseBody?.get(0)?.summary, emptyList(), responseBody?.get(0)?.id)
            return@withContext game
        }
    }

}