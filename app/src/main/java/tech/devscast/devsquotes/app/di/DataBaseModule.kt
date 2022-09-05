package tech.devscast.devsquotes.app.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import tech.devscast.devsquotes.app.room.AppDataBase
import tech.devscast.devsquotes.data.dao.QuotesDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDataBase {
        return Room.databaseBuilder(
            appContext,
            AppDataBase::class.java,
            "devs_quotes"
        ).build()
    }

    @Provides
    @Singleton
    fun provideQuotesDao(appDataBase: AppDataBase): QuotesDao {
        return appDataBase.quotesDao()
    }
}
