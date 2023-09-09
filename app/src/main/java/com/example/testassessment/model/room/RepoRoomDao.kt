package com.example.testassessment.model.room

import javax.inject.Inject

class ListFavoriteMovie @Inject constructor(private val dataMovie: InformationFavoriteDao){
    val getAllFavMovie = dataMovie.getListFavorite()
    suspend fun insertDataFavMovie(movie: DataClassFavoriteDao) = dataMovie.insertAllMovie(movie)

    suspend fun delete(id: Int) = dataMovie.delete(id)
}