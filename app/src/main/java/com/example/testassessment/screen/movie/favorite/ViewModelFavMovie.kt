package com.example.testassessment.screen.movie.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testassessment.model.room.DataClassFavoriteDao
import com.example.testassessment.model.room.ListFavoriteMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelFavMovie @Inject constructor(private val repoFav: ListFavoriteMovie) : ViewModel() {
    val dataListMovie: LiveData<List<DataClassFavoriteDao>> = repoFav.getAllFavMovie
    fun insertMovie(movie: DataClassFavoriteDao) = viewModelScope.launch {
        repoFav.insertDataFavMovie(movie)
    }

    fun deleteMovie(id: Int) = viewModelScope.launch {
        repoFav.delete(id)
    }
}