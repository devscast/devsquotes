package tech.devscast.devsquotes.app.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tech.devscast.devsquotes.data.api.GithubQuotesApi
import tech.devscast.devsquotes.data.datasource.remote.GithubQuotesDataSource
import tech.devscast.devsquotes.data.datasource.remote.RemoteQuotesDataSource

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    fun provideRemoteQuotesDataSource(quotesApi: GithubQuotesApi): RemoteQuotesDataSource {
        return GithubQuotesDataSource(quotesApi)
    }
}
