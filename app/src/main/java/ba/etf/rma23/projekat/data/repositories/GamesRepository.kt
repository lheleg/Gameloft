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
            return@withContext responseBody
        }
    }
}