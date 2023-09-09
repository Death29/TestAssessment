package com.example.testassessment.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movie")
data class DataClassFavoriteDao (
    @PrimaryKey(autoGenerate = true)
    val idMovie: Int,
    val nameMovie: String,
    val rating: Double,
    val poster: String,
    val releaseDate: String
)