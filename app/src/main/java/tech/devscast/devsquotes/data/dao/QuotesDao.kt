package tech.devscast.devsquotes.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import tech.devscast.devsquotes.data.model.RoomQuote

@Dao
interface QuotesDao {
    @Query("SELECT * FROM quotes ORDER BY created_at")
    fun getAll(): Flow<List<RoomQuote>>

    @Query("SELECT * FROM quotes WHERE id = :id LIMIT 1")
    fun getQuotesById(id: String): RoomQuote

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllQuote(quote: List<RoomQuote>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addQuote(quote: RoomQuote)

    @Delete
    suspend fun delete(quote: RoomQuote)
}
