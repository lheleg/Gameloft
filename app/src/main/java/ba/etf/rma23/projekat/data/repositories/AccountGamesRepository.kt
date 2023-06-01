package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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

    suspend fun saveGame(game : Game): Game = withContext(Dispatchers.IO){

            val response = game?.id?.let { game?.title?.let { it1 ->
                AccountApiConfig.retrofit.saveGame(it,
                    it1
                )
            } }
            val responseBody = response?.body()
            val game = Game(responseBody?.title, "","",0.0,"","","", "","","", emptyList(), responseBody?.igdbId)
            return@withContext game

    }
}