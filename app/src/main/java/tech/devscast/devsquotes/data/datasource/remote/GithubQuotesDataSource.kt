package tech.devscast.devsquotes.data.datasource.remote

import tech.devscast.devsquotes.data.api.GithubQuotesApi
import tech.devscast.devsquotes.data.model.QuotesFile
import javax.inject.Inject

class GithubQuotesDataSource @Inject constructor (private val quotesApi: GithubQuotesApi) : RemoteQuotesDataSource {
    override suspend fun getQuotesFiles(
        onSuccess: suspend (List<QuotesFile>) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        try {
            onSuccess(quotesApi.getQuotesFiles())
        } catch (t: Throwable) {
            onFailure(t)
        }
    }
}
