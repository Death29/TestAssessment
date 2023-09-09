package com.example.testassessment.util

import com.example.testassessment.model.response.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {

    //Get All Genre
    @GET("genre/movie/list")
    suspend fun getAllGenre(): Response<ResponseAllGenre>

    //Get Movie By Genre
    @GET("discover/movie")
    suspend fun getMovieByGenre(@Query(Constants.WITH_GENRE) genre: Int, @Query(Constants.PAGE) page: Int): Response<ResponseMovieByGenre>

    //Get Detail Movie
    @GET("movie/{movie_id}")
    suspend fun getDetailMovie(@Path(Constants.MOVIE_ID) movieId: Int): Response<ResponseDetailMovie>

    //Get Review Movie
    @GET("movie/{movie_id}/reviews")
    suspend fun getReview(@Path(Constants.MOVIE_ID) movieId: Int, @Query(Constants.PAGE) page: Int): Response<ResponseReviewMovie>

    //Get Trailer
    @GET("movie/{movie_id}/videos")
    suspend fun getTrailerMovie(@Path(Constants.MOVIE_ID) movieId: Int): Response<ResponseTrailerMovie>
}