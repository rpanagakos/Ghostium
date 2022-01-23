package com.example.ghostzilla.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.ghostzilla.models.CryptoItemDB

@Database(entities = [CryptoItemDB::class], version = 1, exportSchema = false)
@TypeConverters(DatabaseConverter::class)
abstract class GhostzillaDatabase : RoomDatabase() {

    abstract fun cryptoDao(): CryptoDao

}