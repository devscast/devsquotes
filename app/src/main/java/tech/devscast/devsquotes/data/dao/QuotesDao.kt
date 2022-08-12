package tech.devscast.devsquotes.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import tech.devscast.devsquotes.data.model.RoomQuote

@Dao
interface QuotesDao {
    @Query("SELECT * FROM quotes ORDER BY created_at")
    fun getAll(): Flow<List<RoomQuote>>

    // fixme : get nonshown quotes and find a better way to randomize
    @Query("SELECT * FROM quotes ORDER BY created_at LIMIT 1")
    fun getNonShown(): RoomQuote

    @Query("SELECT * FROM quotes WHERE id = :id LIMIT 1")
    fun getQuotesById(id: String): RoomQuote

    @Query("SELECT * FROM quotes WHERE is_favorite = 1")
    fun getAllFavorites() : Flow<List<RoomQuote>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAllQuote(quote: List<RoomQuote>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addQuote(quote: RoomQuote)

    @Update
    suspend fun updateQuote(quote: RoomQuote)

    @Delete
    suspend fun delete(quote: RoomQuote)
}
