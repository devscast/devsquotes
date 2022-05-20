package tech.devscast.devsquotes.data.model

import java.sql.Date

data class Quote(
    val en: String = "",
    val fr: String = "",
    val author: String = "",
    val role: String = ""
) {
    fun toRoomQuote(): RoomQuote {
        return RoomQuote(
            id = en.filterNot { it.isWhitespace() }.filterNot { it == '"' }.take(20),
            en = en,
            fr = fr,
            author = author,
            role = role,
            created_at = Date(System.currentTimeMillis())
        )
    }
}
