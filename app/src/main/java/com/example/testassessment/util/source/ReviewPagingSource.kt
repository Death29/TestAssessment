package com.example.testassessment.util.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.testassessment.model.response.ResultsMovie
import com.example.testassessment.model.response.ResultsReview
import com.example.testassessment.util.Repository

class ReviewPagingSource(
    private val repo: Repository,
    private val idMovie: Int): PagingSource<Int, ResultsReview>(){
    override fun getRefreshKey(state: PagingState<Int, ResultsReview>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultsReview> {
        return try {
            val currentPage = params.key ?: 1
            val response = repo.getReviewMovie(idMovie, currentPage)
            val data = response.body()!!.results

            val responseData = mutableListOf<ResultsReview>()
            responseData.addAll(data)

            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )
        }catch (e: Exception){
            LoadResult.Error(e)
        } catch (e: Exception){
            LoadResult.Error(e)
        }
    }
}