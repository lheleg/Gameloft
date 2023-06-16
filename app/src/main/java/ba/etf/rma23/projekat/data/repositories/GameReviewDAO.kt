package ba.etf.rma23.projekat.data.repositories

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface GameReviewDAO {
    @Query("SELECT * FROM GameReview")
    suspend fun getAll(): List<GameReview>
    @Query("SELECT * FROM GameReview where online=0")
    suspend fun getAllOffline(): List<GameReview>
    @Insert
    suspend fun insertAll(vararg review: GameReview)
    @Query("UPDATE GameReview SET online = 1 where :igdb_id")
    suspend fun updateOnline(igdb_id: Int)
}