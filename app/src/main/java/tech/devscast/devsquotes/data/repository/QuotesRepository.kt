package tech.devscast.devsquotes.data.repository

import tech.devscast.devsquotes.data.datasource.remote.RemoteQuotesDataSource
import javax.inject.Inject

class QuotesRepository @Inject constructor(private val remoteQuotesDataSource: RemoteQuotesDataSource) {

    suspend fun refresh() {
        remoteQuotesDataSource.getQuotesFiles(
            onSuccess = {
            },
            onFailure = {
            }
        )
    }
}
