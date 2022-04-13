package tech.devscast.devsquotes.data.datasource.local

import tech.devscast.devsquotes.data.model.Quote

interface LocalDataSource {
    suspend fun cacheQuotes(quotes: List<Quote>)
}
