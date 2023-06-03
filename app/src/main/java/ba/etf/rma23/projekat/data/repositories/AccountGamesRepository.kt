package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game
import ba.etf.rma23.projekat.UserImpression
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

object AccountGamesRepository{
    private var hash: String = "03a94c19-c784-4847-9314-dbc824d0152c"
    fun setHash(acHash : String): Boolean{
        hash = acHash
        if (hash == "") return false
        return true
    }

    fun getHash(): String {
        return hash
    }

    suspend fun getSavedGames():List<Game> = withContext(Dispatchers.IO) {
        try {
            val response = AccountApiConfig.retrofit.getSavedGames()
            val responseBody = response.body()
            var games = ArrayList<Game>()
            for(i in 0 until responseBody?.size!!){
                games?.add(Game(responseBody?.get(i)?.title, "","",0.0,"","","", "","","", emptyList(), responseBody?.get(i)?.igdbId))
            }
            return@withContext games
        } catch(e: Exception) {
            print(e.message)
            return@withContext emptyList()
        }
    }

    suspend fun saveGame(game: Game): Game?{
        return withContext(Dispatchers.IO) {
            val body = "{\n" +
                    "  \"game\": {\n" +
                    "    \"igdb_id\": "+game?.id+",\n" +
                    "    \"name\": \""+game?.title+"\"\n" +
                    "  }\n" +
                    "}"
            val response = AccountApiConfig.retrofit.saveGame(body.toRequestBody("application/json".toMediaType()))
            val responseBody = response.body()
            val game = Game(responseBody?.title, "","",0.0,"","","", "","","", emptyList(), responseBody?.igdbId)
        return@withContext game
        }
    }

    suspend fun removeGame(id: Int): Boolean = withContext(Dispatchers.IO) {
            val response = AccountApiConfig.retrofit.removeGame(id)
            val responseBody = response.body()
            if (responseBody?.succes == "Games deleted")
                return@withContext true
            return@withContext false
    }
}