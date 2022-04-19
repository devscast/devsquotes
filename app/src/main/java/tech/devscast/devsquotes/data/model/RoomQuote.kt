package tech.devscast.devsquotes.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "quotes")
data class RoomQuote(
    @PrimaryKey
    val id: String,
    val en: String,
    val fr: String,
    val author: String,
    val role: String,
    val created_at: Date
) {
    fun toQuote(): Quote {
        return Quote(
            en = en,
            fr = fr,
            author = author,
            role = role
        )
    }
}
