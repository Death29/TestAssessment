package com.example.testassessment.screen.movie.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
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
import com.example.testassessment.model.room.DataClassFavoriteDao
import com.example.testassessment.screen.movie.favorite.ViewModelFavMovie
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
    private val viewModelFav: ViewModelFavMovie by viewModels()

    private val dataMovie by lazy {
        intent.getIntExtra(Constants.KEY_ID_MOVIE, 0)
    }
    private val adapterListTrailer by lazy {
        AdapterListTrailer()
    }

    private var dataFavMovie = arrayListOf<DataClassFavoriteDao>()

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

            setupDataLifecycleDetail()
            getDataLocal()

//            Setup Rv Review
            rvReviews.apply {
                layoutManager = LinearLayoutManager(
                    this@ActivityDetailMovies,
                    LinearLayoutManager.VERTICAL,
                    false
                )
                adapter = adapterListReview
            }

            rvReviews.adapter = adapterListReview.withLoadStateFooter(
                AdapterLoadMoreData { adapterListReview.retry() }
            )

            rvTrailer.apply {
                layoutManager = LinearLayoutManager(
                    this@ActivityDetailMovies,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                adapter = adapterListTrailer
            }

            //Set Data Detail Movie
            viewModelMovie.liveDataDetailMovie.observe(this@ActivityDetailMovies) {
                setupDataDetail(it)
            }
            //Set Data List Trailer Movie
            viewModelMovie.liveDataTrailer.observe(this@ActivityDetailMovies) {
                adapterListTrailer.setDataTrailer(it.results)
            }
        }
    }

    private fun setupForBookmark(dataDetail: ResponseDetailMovie) {
        binding.apply {
            when (dataFavMovie.isEmpty()) {
                true -> {
                    btnBookmark.text = getString(R.string.bookmark)
                    Log.d("BtnBookmark", "Null Data")
                }

                else -> {
                    for (i in dataFavMovie){
                        Log.d("comparionsid","local: ${i.idMovie} api: ${dataDetail.id}")
                        if (i.idMovie == dataDetail.id) {
                            btnBookmark.text = getString(R.string.unbookmark)
                            Log.d("BtnBookmark", "Not Null, Bookmark")

                            break
                        } else {
                            btnBookmark.text = getString(R.string.bookmark)
                            Log.d("BtnBookmark", "Not Null, not bookmark")
                        }
                    }
                }
            }
        }
    }

    private fun getDataLocal() {
        viewModelFav.dataListMovie.observe(this@ActivityDetailMovies) {
            dataFavMovie = it as ArrayList<DataClassFavoriteDao>

            Log.d("DataLocal", "list: $dataFavMovie")
        }
    }

    private fun setupDataDetail(detailMovie: ResponseDetailMovie) {
        binding.apply {

            //Title Movie
            tvTitle.text = detailMovie.originalTitle.toString()

            //Poster Movie
            val poster = Constants.POSTER_BASE_URL + detailMovie.posterPath
            ivPoster.load(poster) {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_background)
                scale(Scale.FILL)
            }

            //Genre Movie
            for (i in detailMovie.genres!!) {
                tvGenre.append("${i.name} ")
            }

            //Release Date Movie
            tvRelease.text = detailMovie.releaseDate.toString()

            //Rate Movie
            tvRating.text = detailMovie.voteAverage.toString()

            //Tagline Movie
            tvTagline.text = detailMovie.tagline.toString()

            //OverView Movie
            tvOverview.text = detailMovie.overview.toString()

            setupForBookmark(detailMovie)

            btnBookmark.setOnClickListener {
                when (dataFavMovie.isEmpty()) {
                    true -> {
                        val detail = detailMovieToLocal(detailMovie)

                        viewModelFav.insertMovie(detail)
                        btnBookmark.text = getString(R.string.unbookmark)

                        Log.d("operationMovie","first case")
                        Toast.makeText(this@ActivityDetailMovies, "Added Movie", Toast.LENGTH_SHORT).show()
                    }
                    false->{
                        for (i in dataFavMovie){
                            if (i.idMovie == detailMovie.id) {

                                viewModelFav.deleteMovie(i.idMovie)
                                btnBookmark.text = getString(R.string.bookmark)

                                Toast.makeText(
                                    this@ActivityDetailMovies,
                                    "Deleted Movie",
                                    Toast.LENGTH_SHORT
                                ).show()

                                Log.d("operationMovie","Deleted Movie")

                                break
                            }else{
                                val detail = detailMovieToLocal(detailMovie)

                                viewModelFav.insertMovie(detail)
                                btnBookmark.text = getString(R.string.unbookmark)

                                Toast.makeText(this@ActivityDetailMovies, "Added Movie", Toast.LENGTH_SHORT).show()
                                Log.d("operationMovie","Added Movie when list not empty")

                            }
                        }
                    }
                }
            }
        }
    }

    private fun setupDataLifecycleDetail() {
        lifecycleScope.launch {
            viewModelMovie.reviewMovieList(dataMovie).collect {
                adapterListReview.submitData(it)
            }
        }
        //Get Detail
        viewModelMovie.loadDetailMovie(dataMovie)

        //Get Trailer Movie
        viewModelMovie.loadTrailerMovie(dataMovie)
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

    private fun detailMovieToLocal(data: ResponseDetailMovie): DataClassFavoriteDao{
        return DataClassFavoriteDao(data.id!!, data.originalTitle.toString(), data.voteAverage!!, data.posterPath.toString(), data.releaseDate.toString())
    }
}