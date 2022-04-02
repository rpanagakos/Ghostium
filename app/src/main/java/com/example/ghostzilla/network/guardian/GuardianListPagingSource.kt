package com.example.ghostzilla.network.guardian

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.ghostzilla.models.guardian.Result
import com.example.ghostzilla.utils.Constants

const val NETWORK_PAGE_SIZE = 10
private const val INITIAL_LOAD_SIZE = 1

class GuardianListPagingSource(
    private val guardianApi: GuardianApi
) : PagingSource<Int, Result>(){

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        val position = params.key ?: INITIAL_LOAD_SIZE
        return try {
            val guardianResponse = guardianApi.getLatestNewsDummy(Constants.GUARDIAN_CONTENT, "newest", Constants.GUARDIAN_FIELDS, page = position, pageSize = params.loadSize)
            val nextKey = guardianResponse.response.currentPage + 1
            LoadResult.Page(
                data = guardianResponse.response.results,
                prevKey = null, // Only paging forward.
                // assume that if a full page is not loaded, that means the end of the data
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}