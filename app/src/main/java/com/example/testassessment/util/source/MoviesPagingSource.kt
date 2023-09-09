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
            //For first Hit API, default page : 1
            val currentPage = params.key ?: 1
            // Variable for save response from API
            val response = repo.getMovieByGenre(genreId, currentPage)
            // result response to set in some variable to set in adapter
            val data = response.body()!!.results

            //Handling if the result [], throw procces to class NoDataException
            if (data.isEmpty()) {
                throw NoDataException()
            }

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