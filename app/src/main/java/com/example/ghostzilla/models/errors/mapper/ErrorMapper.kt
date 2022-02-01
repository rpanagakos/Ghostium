package com.example.ghostzilla.models.errors.mapper

import android.content.Context
import com.example.ghostzilla.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ErrorMapper @Inject constructor(@ApplicationContext val context: Context) :
    ErrorMapperSource {

    override fun getErrorString(errorId: Int): String {
        return context.getString(errorId)
    }

    override val errorsMap: Map<Int, String>
        get() = mapOf(
            Pair(NO_INTERNET_CONNECTION, getErrorString(R.string.no_internet)),
            Pair(NETWORK_ERROR, getErrorString(R.string.app_name)),
            Pair(SEARCH_ERROR, getErrorString(R.string.nothing_found))
        ).withDefault { getErrorString(R.string.network_error) }
}