package tech.devscast.devsquotes.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import tech.devscast.devsquotes.data.datasource.local.LocalDataSource
import tech.devscast.devsquotes.data.datasource.remote.RemoteQuotesDataSource
import tech.devscast.devsquotes.data.model.Quote
import tech.devscast.devsquotes.util.CsvParser
import tech.devscast.devsquotes.util.toListQuote
import timber.log.Timber
import javax.inject.Inject

class QuotesRepository @Inject constructor(private val remoteQuotesDataSource: RemoteQuotesDataSource, private val localDataSource: LocalDataSource) {

    fun getQuotes(): Flow<List<Quote>> {
        return localDataSource.getQuotes().map { it.toListQuote() }
    }

    fun getNonShownQuotes(): Quote {
        return localDataSource.getNonShownQuotes().toQuote()
    }

    suspend fun refresh() {
        remoteQuotesDataSource.getQuotesFiles(
            onSuccess = { quotesFiles ->
                val quotes = arrayListOf<List<Quote>>()
                for (quotesFile in quotesFiles) {
                    quotes.add(CsvParser.parse(quotesFile))
                }
                localDataSource.cacheQuotes(quotes.flatten())
            },
            onFailure = {
                Timber.e(it)
            }
        )
    }

    suspend fun setAsShown(quote: Quote) {
        localDataSource.setAsShown(quote)
    }

    suspend fun addToFavorite(quote: Quote) {
        localDataSource.addQuoteToFavorites(quote)
    }

    suspend fun removeFromFavorites(quote: Quote) {
        localDataSource.removeFromFavorite(quote)
    }

    fun getFavorites(): Flow<List<Quote>> {
        return localDataSource.getFavoritesQuotes().map { it.toListQuote() }
    }
}
