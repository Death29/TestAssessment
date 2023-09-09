package com.example.testassessment.screen.movie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testassessment.R
import com.example.testassessment.databinding.ActivityListMovieBinding
import com.example.testassessment.screen.movie.detail.ActivityDetailMovies
import com.example.testassessment.screen.movie.favorite.ActivityListFavMovie
import com.example.testassessment.util.AdapterLoadMoreData
import com.example.testassessment.util.Constants
import com.example.testassessment.util.ViewModelMovie
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class ActivityListMovie : AppCompatActivity() {
    private lateinit var binding: ActivityListMovieBinding
    private val viewModelsMovie: ViewModelMovie by viewModels()

    private val dataIntentFromGenre by lazy {
        intent.getIntExtra(Constants.KEY_ID_GENRE, 0)
    }

    @Inject
    lateinit var adapterListMovie: AdapterListMovie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        setupToolbar()
        setupView()
    }

    private fun setupView() {
        binding.apply {

            tvSeeFav.setOnClickListener {
                startActivity(Intent(this@ActivityListMovie, ActivityListFavMovie::class.java))
            }

            lifecycleScope.launch {
                viewModelsMovie.movieList(dataIntentFromGenre).collect {
                    adapterListMovie.submitData(it)
                }
            }

            adapterListMovie.setOnItemClickListener {
                startActivity(
                    Intent(
                        this@ActivityListMovie,
                        ActivityDetailMovies::class.java
                    ).putExtra(Constants.KEY_ID_MOVIE, it.id)
                )
            }

            lifecycleScope.launch {
                adapterListMovie.loadStateFlow.collect {
                    val state = it.refresh
                    pbLoading.isVisible = state is LoadState.Loading
                }
            }

            rvMovie.apply {
                layoutManager =
                    LinearLayoutManager(this@ActivityListMovie, LinearLayoutManager.VERTICAL, false)
                adapter = adapterListMovie
            }

            rvMovie.adapter = adapterListMovie.withLoadStateFooter(
                AdapterLoadMoreData { adapterListMovie.retry() }
            )
        }
    }

    private fun setupToolbar() {
        binding.toolbarListMovie.apply {
            setSupportActionBar(toolbar())
            setTitle(getString(R.string.lbl_movie))
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            supportActionBar?.title = ""
            supportActionBar?.setHomeButtonEnabled(false)
        }
    }
}