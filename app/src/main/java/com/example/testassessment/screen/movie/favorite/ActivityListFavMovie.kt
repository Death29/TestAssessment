package com.example.testassessment.screen.movie.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testassessment.R
import com.example.testassessment.databinding.ActivityListFavoriteMovieBinding
import com.example.testassessment.model.room.DataClassFavoriteDao
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityListFavMovie : AppCompatActivity() {
    private lateinit var binding: ActivityListFavoriteMovieBinding

    private val vmFavMovie: ViewModelFavMovie by viewModels()
    private lateinit var adapterFavMovie: AdapterListFavorite

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListFavoriteMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        setupToolbar()
        setupView()
    }

    private fun setupView() {
        adapterFavMovie = AdapterListFavorite(this@ActivityListFavMovie)

        binding.apply {
            rvListFav.apply {
                layoutManager = LinearLayoutManager(
                    this@ActivityListFavMovie,
                    LinearLayoutManager.VERTICAL,
                    false
                )
                setHasFixedSize(true)
                adapter = adapterFavMovie
            }

            vmFavMovie.dataListMovie.observe(this@ActivityListFavMovie) {
                setDataFav(it)
            }
        }
    }

    private fun setDataFav(movie: List<DataClassFavoriteDao>?) {

        Log.d("DataLocalFav", movie.toString())
        binding.apply {
            pbLoading.visibility = GONE

            if (movie?.isEmpty() == true)layoutNoData.visibility= VISIBLE
            else {
                layoutNoData.visibility = GONE
                adapterFavMovie.setDataFav(movie!!)
            }

            rvListFav.isVisible = !layoutNoData.isVisible
        }
    }

    private fun setupToolbar() {
        binding.customToolbar.apply {
            setSupportActionBar(toolbar())
            setTitle(getString(R.string.lbl_fav_movie))
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            supportActionBar?.title = ""
            supportActionBar?.setHomeButtonEnabled(false)
        }
    }
}