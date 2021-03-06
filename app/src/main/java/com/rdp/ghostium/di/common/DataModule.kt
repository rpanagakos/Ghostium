package com.rdp.ghostium.di.common

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.rdp.ghostium.connectivity.ConnectivityObserver
import com.rdp.ghostium.connectivity.ConnectivityObserverImpl
import com.rdp.ghostium.connectivity.connectivityManager
import com.rdp.ghostium.database.room.GhostzillaDatabase
import com.rdp.ghostium.utils.Constants.Companion.GHOSTZILLA_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataModule {

    @Singleton
    @Provides
    fun dataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        preferencesDataStore(name = "data-store").getValue(context, String::javaClass)

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
            context,
            GhostzillaDatabase::class.java,
            GHOSTZILLA_NAME
        ).build()


    @Singleton
    @Provides
    fun provideCryptoDao(database: GhostzillaDatabase) = database.cryptoDao()

    @Singleton
    @Provides
    fun provideSearchDao(database: GhostzillaDatabase) = database.searchDao()

    @Singleton
    @Provides
    fun provideTrendingCryptosDao(database: GhostzillaDatabase) = database.trendingDao()

    @Singleton
    @Provides
    fun provideArticlessDao(database: GhostzillaDatabase) = database.articlesDao()

    @Singleton
    @Provides
    fun provideConnectivityObserver(application: Application): ConnectivityObserver {
        return ConnectivityObserverImpl(application.connectivityManager)
    }

}