package com.kcthomas.matchtracker.di

import android.content.Context
import androidx.room.Room
import com.kcthomas.data.match.CachedMatchSource
import com.kcthomas.data.match.MatchRepositoryImpl
import com.kcthomas.data.player.CachedPlayerSource
import com.kcthomas.data.player.PlayerRepositoryImpl
import com.kcthomas.data.room.APP_DATABASE_NAME
import com.kcthomas.data.room.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context) =
        Room
            .databaseBuilder(context, AppDatabase::class.java, APP_DATABASE_NAME)
            .build()

    @Provides
    @Singleton
    fun provideCachedMatchSource(appDatabase: AppDatabase) =
        CachedMatchSource(appDatabase)

    @Provides
    @Singleton
    fun provideMatchRepository(cachedSource: CachedMatchSource) =
        MatchRepositoryImpl(cachedSource)

    @Provides
    @Singleton
    fun provideCachedPlayerSource(appDatabase: AppDatabase) =
        CachedPlayerSource(appDatabase)

    @Provides
    @Singleton
    fun providePlayerRepository(cachedSource: CachedPlayerSource) =
        PlayerRepositoryImpl(cachedSource)

}
