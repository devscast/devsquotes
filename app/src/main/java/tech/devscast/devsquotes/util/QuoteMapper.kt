package tech.devscast.devsquotes.util

import tech.devscast.devsquotes.data.model.Quote
import tech.devscast.devsquotes.data.model.RoomQuote

fun List<RoomQuote>.toListQuote(): List<Quote> {
    val quotes = arrayListOf<Quote>()
    for (quote in this) {
        quotes.add(quote.toQuote())
    }
    return quotes
}

fun List<Quote>.toListRoomQuote(): List<RoomQuote> {
    val roomQuotes = arrayListOf<RoomQuote>()
    for (roomQuote in this) {
        roomQuotes.add(roomQuote.toRoomQuote())
    }
    return roomQuotes
}
