package com.example.testassessment.model.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DataClassFavoriteDao::class], version = 1)
abstract class AppDatabase: RoomDatabase(){
    abstract fun listMovie(): InformationFavoriteDao
}