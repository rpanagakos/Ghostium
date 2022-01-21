package com.example.ghostzilla.database.room

import androidx.room.*
import com.example.ghostzilla.models.coingecko.CryptoItem

@Database(entities = [CryptoItem::class], version = 1, exportSchema = false)
@TypeConverters(DatabaseConverter::class)
abstract class GhostlyDatabase: RoomDatabase() {

    abstract fun cryptoDao(): CryptoDao

}