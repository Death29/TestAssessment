package com.example.testassessment.util

import javax.inject.Inject

class Repository @Inject constructor(private val apiServices: ApiServices) {
    suspend fun getAllGenre() = apiServices.getAllGenre()
    suspend fun getMovieByGenre(genre: Int, page: Int) = apiServices.getMovieByGenre(genre, page)
    suspend fun getDetailMovie(idMovie: Int) = apiServices.getDetailMovie(idMovie)
    suspend fun getReviewMovie(idMovie: Int, page: Int) = apiServices.getReview(idMovie, page)
    suspend fun getTrailerMovie(idMovie: Int) = apiServices.getTrailerMovie(idMovie)
}