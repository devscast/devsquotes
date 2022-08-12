package tech.devscast.devsquotes.data.datasource.local

import kotlinx.coroutines.flow.Flow
import tech.devscast.devsquotes.data.dao.QuotesDao
import tech.devscast.devsquotes.data.model.Quote
import tech.devscast.devsquotes.data.model.RoomQuote
import tech.devscast.devsquotes.util.toListRoomQuote
import timber.log.Timber
import javax.inject.Inject

class RoomQuotesDataSource @Inject constructor(private val quotesDao: QuotesDao) : LocalDataSource {
    override suspend fun cacheQuotes(quotes: List<Quote>) {
        Timber.e(quotes.toString())
        quotesDao.addAllQuote(quotes.toListRoomQuote())
        Timber.e(quotes.toListRoomQuote().toString())
    }

    override fun getQuotes(): Flow<List<RoomQuote>> {
        return quotesDao.getAll()
    }

    override fun getNonShownQuotes(): RoomQuote {
        return quotesDao.getNonShown()
    }

    override suspend fun addQuoteToFavorites(quote: Quote) {
        val mQuote = quote.copy(is_favorite = true).toRoomQuote()
        quotesDao.updateQuote(quote = mQuote)
    }

    override suspend fun removeFromFavorite(quote: Quote) {
        val mQuote = quote.copy(is_favorite = false).toRoomQuote()
        quotesDao.updateQuote(quote = mQuote)
    }

    override fun getFavoritesQuotes(): Flow<List<RoomQuote>> {
        return quotesDao.getAllFavorites()
    }
}
