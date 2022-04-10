package tech.devscast.devsquotes.data.api

import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import tech.devscast.devsquotes.data.model.GithubQuotesResponse

interface GithubQuotesApi {

    @GET("?ref=master")
    suspend fun getQuotesFiles(): Flow<GithubQuotesResponse>
}
