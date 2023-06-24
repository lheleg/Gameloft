package ba.etf.rma23.projekat.data.repositories

import android.content.Context
import ba.etf.rma23.projekat.Game
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

object GameReviewsRepository {
    suspend fun sendReview(context: Context, gameReview: GameReview): Boolean{
        return withContext(Dispatchers.IO) {
            try {
            val savedGames = AccountGamesRepository.getSavedGames()
            var gameSaved = false
            for( game in  savedGames){
                if ( gameReview.igdb_id == game.id){
                    gameSaved = true
                    break
                }
            }
            if(!gameSaved){
                AccountGamesRepository.saveGame(Game(gameReview.igdb_id,"slatkica",null,null,null,null,null,null,null,null,null, emptyList()))
            }
            var review : String? = null
            if (gameReview.review != null) review = "\""+gameReview.review+"\""
            val body = "{\n" +
                    "    \"review\": "+review+",\n" +
                    "    \"rating\": "+gameReview.rating+"\n" +
                    "}"

                val response = AccountApiConfig.retrofit.sendReview(
                    body.toRequestBody("application/json".toMediaType()),
                    gameReview.igdb_id
                )

            }catch(e: Exception){
                gameReview.online = false
                var db = AppDatabase.getInstance(context)
                db!!.gameReviewDao().insertAll(gameReview)
                return@withContext false
            }

            return@withContext true
        }
    }

    suspend fun getReviewsForGame(igdb_id: Int):List<GameReview>{
        return withContext(Dispatchers.IO) {
            val response = AccountApiConfig.retrofit.getReviewsForGame(igdb_id)
            return@withContext response.body()!!
        }
    }

    suspend fun getOfflineReviews(context: Context): List<GameReview>{
        return withContext(Dispatchers.IO) {
            var db = AppDatabase.getInstance(context)
            return@withContext db!!.gameReviewDao().getAllOffline()
        }
    }

    suspend fun sendOfflineReviews(context: Context): Int{
        return withContext(Dispatchers.IO){

            var count = 0
            val reviews = getOfflineReviews(context)
            var db = AppDatabase.getInstance(context)
            for (review in reviews){

                val response = sendReview(context,
                    GameReview(review.rating,review.review,review.igdb_id)
                )
                if(response) {
                    count++
                    db!!.gameReviewDao().updateOnline(review.igdb_id)
                }
            }
            return@withContext count
        }
    }
}