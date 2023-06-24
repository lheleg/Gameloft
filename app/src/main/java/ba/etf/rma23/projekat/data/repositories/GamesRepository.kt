package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game
import ba.etf.rma23.projekat.GameListAdapter
import ba.etf.rma23.projekat.HomeFragment
import ba.etf.rma23.projekat.UserImpression
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

object GamesRepository {
     lateinit var displeyedGames: List<Game>
    suspend fun getGamesByName(search : String
    ) : List<Game>? {
        return withContext(Dispatchers.IO) {
            var response = IGDBApiConfig.retrofit.getGamesByName(search)
            val responseBody = response.body()
            var games = ArrayList<Game>()
            for(i in 0 until responseBody?.size!!){
                games?.add(Game( responseBody?.get(i)?.id,responseBody?.get(i)?.title,responseBody?.get(i)?.platforms?.get(0)?.name,responseBody?.get(i)?.release?.get(0)?.human, responseBody?.get(i)?.rating,responseBody?.get(i)?.cover?.coverUrl,
                    getAgeRating(responseBody?.get(0)?.ageRating),"","","", responseBody?.get(i)?.summary,
                    emptyList()
                ))
            }
            displeyedGames = games
            return@withContext games
        }
    }
    suspend fun getGamesSafe(search : String
    ) : List<Game>? {
        return withContext(Dispatchers.IO) {
            var response = IGDBApiConfig.retrofit.getGamesByName(search)
            val responseBody = response.body()
            var games = ArrayList<Game>()
            for(i in 0 until responseBody?.size!!){
                if(getAgeRating(responseBody?.get(0)?.ageRating) != "5" && getAgeRating(responseBody?.get(0)?.ageRating) != "12" && getAgeRating(responseBody?.get(0)?.ageRating) != "11")
                    games?.add(Game(responseBody?.get(i)?.id,responseBody?.get(i)?.title,responseBody?.get(i)?.platforms?.get(0)?.name,responseBody?.get(i)?.release?.get(0)?.human, responseBody?.get(i)?.rating,responseBody?.get(i)?.cover?.coverUrl,
                        getAgeRating(responseBody?.get(0)?.ageRating),"","","", responseBody?.get(i)?.summary, emptyList()
                    ))
            }
            displeyedGames = games
            return@withContext games
        }
    }
    suspend fun getGameById(id : Int): Game?{
        return withContext(Dispatchers.IO) {
            val body =
                "fields id,name,platforms.name,involved_companies.company.name," +
                        "release_dates.human,rating,cover.url,genres.name,summary," +
                        "age_ratings.rating,age_ratings.category; where id = $id;"
            val response = IGDBApiConfig.retrofit.getGamesById(
                "vtfllttx3gwlzv55oajmcrbsswxpl0",
                "Bearer ffhb27f1jylitms082osnbkxwvvkfs",
                body.toRequestBody("text/plain".toMediaType())
            )
            val responseBody = response.body()
            return@withContext Game(
                responseBody?.get(0)?.id,
                responseBody?.get(0)?.title,
                responseBody?.get(0)?.platforms?.get(0)?.name,
                responseBody?.get(0)?.release?.get(0)?.human,
                responseBody?.get(0)?.rating,
                responseBody?.get(0)?.cover?.coverUrl,
                getAgeRating(responseBody?.get(0)?.ageRating),
                responseBody?.get(0)?.i_companies?.get(0)?.company?.name,
                responseBody?.get(0)?.i_companies?.get(0)?.company?.name,
                responseBody?.get(0)?.genre?.get(0)?.name,
                responseBody?.get(0)?.summary,
                emptyList<UserImpression>()
            )
        }
    }
    private fun getAgeRating(ageRating: List<AgeRating>?): String? {
        if (ageRating == null) return null
        for (i in 0 until ageRating?.size as Int){
            if (ageRating?.get(i)?.category == 2 || ageRating?.get(i)?.category == 1)
                return ageRating?.get(i)?.rating.toString()
        }
        return ""
    }
    suspend fun sortGames(): List<Game>{
        var games = ArrayList<Game>()
        var sGames = ArrayList<Game>()
        var currentGames = displeyedGames
        var temp = false
        val savedGames = AccountGamesRepository.getSavedGames()
        for (game in currentGames){
            for (sGame in savedGames){
                if(game?.id == sGame?.id) {
                    sGames?.add(game)
                    temp = true
                    break
                }
            }
            if(!temp){
                games?.add(game)
            }
            temp = false
        }
        sGames.sortBy { it.title }
        games.sortBy { it.title }
        for (game in games){
            sGames?.add(game)
        }
        displeyedGames = sGames
        return sGames
    }
}