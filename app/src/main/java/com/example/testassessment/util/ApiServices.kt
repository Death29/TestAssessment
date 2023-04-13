package com.example.testassessment.util

import com.example.testassessment.model.request.RequestBodyLogin
import com.example.testassessment.model.response.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {

    //For Guest Session
    @GET("authentication/guest_session/new")
    suspend fun getGuestSession(@Query(Constants.API) api: String): Response<ResponseGuestSession>

    //Get All Genre
    @GET("genre/movie/list")
    suspend fun getAllGenre(@Query(Constants.API) api: String): Response<ResponseAllGenre>

    //Get Movie By Genre
    @GET("discover/movie")
    suspend fun getMovieByGenre(@Query(Constants.API) api: String, @Query(Constants.WITH_GENRE) genre: Int, @Query(Constants.PAGE) page: Int): Response<ResponseMovieByGenre>

    //Get Detail Movie
    @GET("movie/{movie_id}")
    suspend fun getDetailMovie(@Path(Constants.MOVIE_ID) movieId: Int, @Query(Constants.API) api: String): Response<ResponseDetailMovie>

    //Get Review Movie
    @GET("movie/{movie_id}/reviews")
    suspend fun getReview(@Path(Constants.MOVIE_ID) movieId: Int, @Query(Constants.API) api: String, @Query(Constants.PAGE) page: Int): Response<ResponseReviewMovie>

    //Get Trailer
    @GET("movie/{movie_id}/videos")
    suspend fun getTrailerMovie(@Path(Constants.MOVIE_ID) movieId: Int, @Query(Constants.API) api: String): Response<ResponseTrailerMovie>
}