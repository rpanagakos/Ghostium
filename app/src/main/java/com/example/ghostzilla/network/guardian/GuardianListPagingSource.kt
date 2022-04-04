package com.example.ghostzilla.network.guardian

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.ghostzilla.abstraction.LocalModel
import com.example.ghostzilla.models.settings.TitleRecyclerItem
import com.example.ghostzilla.utils.Constants

const val NETWORK_PAGE_SIZE = 10
private const val INITIAL_LOAD_SIZE = 1

class GuardianListPagingSource(
    private val guardianApi: GuardianApi,
    private val title : TitleRecyclerItem
) : PagingSource<Int, LocalModel>() {

    override fun getRefreshKey(state: PagingState<Int, LocalModel>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LocalModel> {
        val position = params.key ?: INITIAL_LOAD_SIZE
        return try {
            val guardianResponse = guardianApi.getLatestNews(
                Constants.GUARDIAN_CONTENT, "newest", Constants.GUARDIAN_FIELDS,
                page = position, pageSize = params.loadSize
            )
            val finalList =
                if (guardianResponse.response.currentPage == 1)
                    listOf(title).plus(guardianResponse.response.articles)
                else
                    guardianResponse.response.articles
            val nextKey = guardianResponse.response.currentPage + 1
            LoadResult.Page(
                data = finalList,
                prevKey = null, // Only paging forward.
                // assume that if a full page is not loaded, that means the end of the data
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}