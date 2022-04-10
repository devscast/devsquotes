package tech.devscast.devsquotes.data.datasource.remote

import kotlinx.coroutines.flow.collect
import tech.devscast.devsquotes.data.api.GithubQuotesApi
import tech.devscast.devsquotes.data.model.QuotesFile

class GithubQuotesDataSource(private val quotesApi: GithubQuotesApi) : RemoteQuotesDataSource {
    override suspend fun getQuotesFiles(
        onSuccess: (List<QuotesFile>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        try {
            quotesApi.getQuotesFiles().collect {
                onSuccess(it.quotesFiles)
            }
        } catch (t: Throwable) {
            onFailure(t)
        }
    }
}
