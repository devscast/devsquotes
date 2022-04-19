package tech.devscast.devsquotes.app.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tech.devscast.devsquotes.data.api.GithubQuotesApi
import tech.devscast.devsquotes.data.dao.QuotesDao
import tech.devscast.devsquotes.data.datasource.local.LocalDataSource
import tech.devscast.devsquotes.data.datasource.local.RoomQuotesDataSource
import tech.devscast.devsquotes.data.datasource.remote.GithubQuotesDataSource
import tech.devscast.devsquotes.data.datasource.remote.RemoteQuotesDataSource

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    fun provideRemoteQuotesDataSource(quotesApi: GithubQuotesApi): RemoteQuotesDataSource {
        return GithubQuotesDataSource(quotesApi)
    }

    @Provides
    fun provideLocaleDataSource(quotesDao: QuotesDao): LocalDataSource {
        return RoomQuotesDataSource(quotesDao)
    }
}
