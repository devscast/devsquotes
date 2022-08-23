package tech.devscast.devsquotes.data.datasource.local

import kotlinx.coroutines.flow.Flow
import tech.devscast.devsquotes.data.model.Quote
import tech.devscast.devsquotes.data.model.RoomQuote

interface LocalDataSource {
    suspend fun cacheQuotes(quotes: List<Quote>)
    fun getQuotes(): Flow<List<RoomQuote>>
    fun getNonShownQuotes(): RoomQuote
    suspend fun addQuoteToFavorites(quote: Quote)
    suspend fun removeFromFavorite(quote: Quote)
    fun getFavoritesQuotes(): Flow<List<RoomQuote>>
}
