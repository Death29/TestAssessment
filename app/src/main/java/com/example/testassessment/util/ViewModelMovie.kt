package com.example.testassessment.util

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.testassessment.model.response.*
import com.example.testassessment.util.source.MoviesPagingSource
import com.example.testassessment.util.source.ReviewPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelMovie @Inject constructor(private val repo: Repository):ViewModel() {
    val loading = MutableLiveData<Boolean>()
    val errorMsg = MutableLiveData<String>()

    //Get Genre
    val liveDataGenre = MutableLiveData<ResponseAllGenre>()
    fun loadGenre() = viewModelScope.launch {
        loading.postValue(true)

        val response = repo.getAllGenre()
        if (response.isSuccessful) liveDataGenre.postValue(response.body())
        else errorMsg.postValue(response.message())

        loading.postValue(false)
    }

    //Get Movie
    fun movieList(genre: Int) = Pager(PagingConfig(1)){
        MoviesPagingSource(repo, genre)
    }.flow.cachedIn(viewModelScope)

    //Get Detail Movie
    val liveDataDetailMovie = MutableLiveData<ResponseDetailMovie>()
    fun loadDetailMovie(idMovie: Int) = viewModelScope.launch {
        loading.postValue(true)

        val response = repo.getDetailMovie(idMovie)
        if (response.isSuccessful) liveDataDetailMovie.postValue(response.body())
        else errorMsg.postValue(response.message())

        loading.postValue(false)
    }

    //Get Review
    fun reviewMovieList(idMovie: Int) = Pager(PagingConfig(1)){
        ReviewPagingSource(repo, idMovie)
    }.flow.cachedIn(viewModelScope)

    //Get Trailer
    val liveDataTrailer = MutableLiveData<ResponseTrailerMovie>()
    fun loadTrailerMovie(idMovie: Int) = viewModelScope.launch {
        loading.postValue(true)

        val response = repo.getTrailerMovie(idMovie)
        if (response.isSuccessful) liveDataTrailer.postValue(response.body())
        else errorMsg.postValue(response.message())

        loading.postValue(false)
    }
}