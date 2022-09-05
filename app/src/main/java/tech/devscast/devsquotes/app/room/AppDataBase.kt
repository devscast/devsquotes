package tech.devscast.devsquotes.app.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import tech.devscast.devsquotes.data.dao.QuotesDao
import tech.devscast.devsquotes.data.model.RoomQuote

@Database(entities = [RoomQuote::class], version = 1, exportSchema = false)
@TypeConverters(RoomConverters::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun quotesDao(): QuotesDao
}
