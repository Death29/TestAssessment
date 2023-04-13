package com.example.testassessment.screen.movie.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.size.Scale
import com.example.testassessment.R
import com.example.testassessment.databinding.ActivityDetailMoviesBinding
import com.example.testassessment.model.response.ResponseDetailMovie
import com.example.testassessment.util.AdapterLoadMoreData
import com.example.testassessment.util.Constants
import com.example.testassessment.util.ViewModelMovie
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ActivityDetailMovies : AppCompatActivity() {
    private lateinit var binding: ActivityDetailMoviesBinding

    private val viewModelMovie: ViewModelMovie by viewModels()
    private val dataMovie by lazy {
        intent.getIntExtra(Constants.KEY_ID_MOVIE, 0)
    }

    private var seeTrailer = false
    private var seeReview = false

    @Inject
    lateinit var adapterListReview: AdapterListReview

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        setupToolbar()
        setupView()
    }

    private fun setupView() {
        binding.apply {

            setupLifecycleReview()

            //Setup Rv Review
            rvReview.apply {
                layoutManager = LinearLayoutManager(
                    this@ActivityDetailMovies,
                    LinearLayoutManager.VERTICAL,
                    false
                )
                adapter = adapterListReview
            }
            rvReview.adapter = adapterListReview.withLoadStateFooter(
                AdapterLoadMoreData { adapterListReview.retry() }
            )

            //Get Detail
            viewModelMovie.loadDetailMovie(dataMovie)

            //Get Trailer Movie
            viewModelMovie.loadTrailerMovie(dataMovie)

            //Set Detail
            viewModelMovie.liveDataDetailMovie.observe(this@ActivityDetailMovies) {
                setupDataDetail(it)
            }

            viewModelMovie.liveDataTrailer.observe(this@ActivityDetailMovies) {
                lifecycle.addObserver(binding.mediaPlayerVideo)
                binding.mediaPlayerVideo.apply {
                    addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            super.onReady(youTubePlayer)

                            it.results.forEach { it ->
                                if (it.name == Constants.FINAL_TRAILER) youTubePlayer.loadVideo(
                                    it.key.toString(),
                                    0f
                                )
                            }
                        }
                    })
                }
            }

            tvToTrailer.setOnClickListener {
                when (seeTrailer) {
                    true -> {
                        seeTrailer = false
                        tvStateTrailer.text = getString(R.string.txt_see_trailer)
                        mediaPlayerVideo.visibility = GONE
                    }
                    false -> {
                        seeTrailer = true
                        tvStateTrailer.text = getString(R.string.txt_hide_trailer)
                        mediaPlayerVideo.visibility = VISIBLE
                    }
                }
            }

            tvToReview.setOnClickListener {
                when (seeReview) {
                    true -> {
                        seeReview = false
                        titleState.text= getString(R.string.txt_see_reviews)
                        rvReview.visibility = GONE
                    }

                    false -> {
                        seeReview = true
                        titleState.text= getString(R.string.txt_hide_reviews)
                        rvReview.visibility = VISIBLE
                    }
                }
            }
        }
    }

    private fun setupDataDetail(it: ResponseDetailMovie) {
        binding.apply {
            //Title
            tvTitle.text = it.originalTitle.toString()

            //Poster
            val poster = Constants.POSTER_BASE_URL + it.posterPath
            ivPoster.load(poster) {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_background)
                scale(Scale.FILL)
            }

            //Genre
            for (i in it.genres!!){
                tvGenre.append("${i.name} ")
            }

            //Release
            tvRelease.text = it.releaseDate.toString()

            //Rate
            tvRating.text = it.voteAverage.toString()

            //Tagline
            tvTagline.text = it.tagline.toString()

            //OverView
            tvOverview.text = it.overview.toString()
        }
    }

    private fun setupLifecycleReview() {
        lifecycleScope.launch {
            viewModelMovie.reviewMovieList(dataMovie).collect {
                adapterListReview.submitData(it)
            }
        }

        lifecycleScope.launch {
            adapterListReview.loadStateFlow.collect {
                val state = it.refresh
                binding.pbLoading.isVisible = state is LoadState.Loading
            }
        }
    }

    private fun setupToolbar() {
        binding.customToolbar.apply {
            setSupportActionBar(toolbar())
            setTitle(getString(R.string.lbl_detail))
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            supportActionBar?.title = ""
            supportActionBar?.setHomeButtonEnabled(false)
        }
    }
}