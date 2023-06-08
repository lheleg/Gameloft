package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game
import ba.etf.rma23.projekat.data.repositories.GamesRepository.getGameById
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

object AccountGamesRepository{
    private var hash: String = ""
    private var age: Int? = null

    fun setHash(acHash : String): Boolean{
        hash = acHash
        if (hash == "") return false
        return true
    }
    fun setAge(age : Int?): Boolean{
        if (age in 3..100){
            AccountGamesRepository.age = age
            return true
        }
        return false
    }
    fun getHash(): String {
        return hash
    }

    fun getAge(): Int? {
        return age
    }

    suspend fun getSavedGames():List<Game> = withContext(Dispatchers.IO) {
        try {
            val response = AccountApiConfig.retrofit.getSavedGames()
            val responseBody = response.body()
            var games = ArrayList<Game>()
            for(i in 0 until responseBody?.size!!){
                games?.add(Game(responseBody?.get(i)?.igdbId,responseBody?.get(i)?.title, "","",0.0,"","","", "","","", emptyList()))
            }
            for (game in games){
                val result = game.id?.let { getGameById(it) }
                game.title = result?.title
                game.platform = result?.platform
                game.releaseDate = result?.releaseDate
                game.rating = result?.rating
                game.coverImage = result?.coverImage
                game.genre = result?.genre
                game.description = result?.description
            }
            GamesRepository.displeyedGames = games
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
            val game = Game(responseBody?.igdbId,responseBody?.title, "","",0.0,"","","", "","","", emptyList())
        return@withContext game
        }
    }

    suspend fun removeGame(id: Int?): Boolean = withContext(Dispatchers.IO) {
        val response = id?.let { AccountApiConfig.retrofit.removeGame(it) }
        val responseBody = response?.body()
        if (responseBody?.success == "Games deleted")
            return@withContext true
        return@withContext false
    }
    suspend fun getGamesContainingString(query:String): List<Game>{
        val games = getSavedGames()
        for (game in games){
            val result = game.id?.let { GamesRepository.getGameById(it) }
            game.title = result?.title
            game.platform = result?.platform
            game.releaseDate = result?.releaseDate
            game.rating = result?.rating
            game.coverImage = result?.coverImage
            game.genre = result?.genre
            game.description = result?.description
        }
        val temp = ArrayList<Game>()
        for(game in games){
            val regex = query.toRegex(RegexOption.IGNORE_CASE)
            if(regex.containsMatchIn(game?.title.toString()))
                temp.add(game)
        }
        GamesRepository.displeyedGames = temp
        return temp
    }

    suspend fun removeNonSafe(): Boolean = withContext(Dispatchers.IO) {
        val games = getSavedGames()
        for (g in games) {
            val game = g.id?.let { getGameById(it) }
            if (game?.esrbRating == "5" || game?.esrbRating == "12" || game?.esrbRating == "11") {
                val response = game?.id?.let { AccountApiConfig.retrofit.removeGame(it) }
                val responseBody = response?.body()
                if (responseBody?.success != "Games deleted")
                    return@withContext false
            }
        }
            return@withContext true
    }

}