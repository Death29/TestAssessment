package com.example.testassessment.util.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.testassessment.model.response.ResponseMovieByGenre
import com.example.testassessment.model.response.ResultsMovie
import com.example.testassessment.util.Repository

class MoviesPagingSource(private val repo: Repository, private val genreId: Int): PagingSource<Int, ResultsMovie>() {
    override fun getRefreshKey(state: PagingState<Int, ResultsMovie>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultsMovie> {
        return try {
            val currentPage = params.key ?: 1
            val response = repo.getMovieByGenre(genreId, currentPage)
            val data = response.body()!!.results

            val responseData = mutableListOf<ResultsMovie>()
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