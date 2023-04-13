package com.example.testassessment.util

import com.example.testassessment.model.request.RequestBodyLogin
import javax.inject.Inject

class Repository @Inject constructor(private val apiServices: ApiServices) {
    suspend fun getSession() = apiServices.getGuestSession(Constants.API_KEY)
    suspend fun getAllGenre() = apiServices.getAllGenre(Constants.API_KEY)
    suspend fun getMovieByGenre(genre: Int, page: Int) = apiServices.getMovieByGenre(Constants.API_KEY, genre, page)
    suspend fun getDetailMovie(idMovie: Int) = apiServices.getDetailMovie(idMovie, Constants.API_KEY)
    suspend fun getReviewMovie(idMovie: Int, page: Int) = apiServices.getReview(idMovie, Constants.API_KEY, page)
    suspend fun getTrailerMovie(idMovie: Int) = apiServices.getTrailerMovie(idMovie, Constants.API_KEY)
}