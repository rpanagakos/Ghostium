package com.example.ghostzilla.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.ghostzilla.database.room.GhostzillaDatabase
import com.example.ghostzilla.utils.Constants.Companion.GHOSTZILLA_NAME
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
    fun provideDao(database: GhostzillaDatabase) = database.cryptoDao()

}