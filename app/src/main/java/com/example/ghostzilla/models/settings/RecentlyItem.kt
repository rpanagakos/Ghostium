package com.example.ghostzilla.models.settings

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.ghostzilla.abstraction.LocalModel

@Entity(tableName = "searches_table")
data class RecentlyItem(
    @PrimaryKey(autoGenerate = false)
    val searchedText : String): LocalModel {

    override fun equalsContent(obj: LocalModel): Boolean {
        return true
    }
}