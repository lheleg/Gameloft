package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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

}