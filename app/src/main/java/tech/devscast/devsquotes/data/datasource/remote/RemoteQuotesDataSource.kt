package tech.devscast.devsquotes.data.datasource.remote

import tech.devscast.devsquotes.data.model.QuotesFile

interface RemoteQuotesDataSource {
    suspend fun getQuotesFiles(onSuccess: suspend (List<QuotesFile>) -> Unit, onFailure: (Throwable) -> Unit)
}
