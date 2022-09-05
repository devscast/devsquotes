package tech.devscast.devsquotes.data.api

import retrofit2.http.GET
import tech.devscast.devsquotes.data.model.QuotesFile

interface GithubQuotesApi {

    @GET("?ref=master")
    suspend fun getQuotesFiles(): List<QuotesFile>
}
