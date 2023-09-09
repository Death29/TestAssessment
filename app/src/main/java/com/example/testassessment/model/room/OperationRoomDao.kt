package com.example.testassessment.model.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface InformationFavoriteDao{
    @Query("SELECT * FROM favorite_movie")
    fun getListFavorite(): LiveData<List<DataClassFavoriteDao>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMovie(movie: DataClassFavoriteDao)

    @Query("DELETE FROM favorite_movie WHERE idMovie = :id")
    suspend fun delete(id: Int)
}