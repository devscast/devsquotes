package tech.devscast.devsquotes.data.model

import java.sql.Date

data class Quote(
    val en: String = "",
    val fr: String = "",
    val author: String = "",
    val role: String = "",
    val is_favorite: Boolean = false,
    val is_quote_shown: Boolean = false
) {
    fun toRoomQuote(): RoomQuote {
        return RoomQuote(
            id = en.filterNot { it.isWhitespace() }.filterNot { it == '"' }.take(20),
            en = en,
            fr = fr,
            author = author,
            role = role,
            created_at = Date(System.currentTimeMillis()),
            is_favorite = is_favorite,
            is_quote_shown = is_quote_shown
        )
    }
}

val Quote.generatedId: String
    get() = en.filterNot { it.isWhitespace() }.filterNot { it == '"' }.take(20)

fun Quote.getUrl(): String = "https://quotes.devscast.tech/$generatedId"
