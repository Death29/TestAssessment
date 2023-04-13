package com.example.testassessment.screen.genre

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.testassessment.R
import com.example.testassessment.databinding.ActivityListGenreBinding
import com.example.testassessment.model.response.DataGenre
import com.example.testassessment.screen.movie.ActivityListMovie
import com.example.testassessment.util.Constants
import com.example.testassessment.util.ViewModelMovie
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ActivityListGenre : AppCompatActivity() {
    private lateinit var binding: ActivityListGenreBinding
    private val viewModelMovie: ViewModelMovie by viewModels()

    private val adapterGenre by lazy {
        AdapterListGenre { item -> goToListMovie(item) }
    }

    private fun goToListMovie(item: DataGenre) {
        startActivity(Intent(this@ActivityListGenre, ActivityListMovie::class.java).putExtra(Constants.KEY_ID_GENRE, item.id))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListGenreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        setupView()
        setupToolbar()
    }

    private fun setupView() {
        binding.apply {
            rvGenre.apply {
                layoutManager = GridLayoutManager(this@ActivityListGenre, 2)
                adapter = adapterGenre
                setHasFixedSize(true)
            }

            viewModelMovie.loadGenre()
            viewModelMovie.liveDataGenre.observe(this@ActivityListGenre){
                adapterGenre.setDataGenre(it.genres)
            }

            viewModelMovie.loading.observe(this@ActivityListGenre){
                if (it) {
                    rvGenre.visibility = GONE
                    pbLoading.visibility = VISIBLE
                }else{
                    rvGenre.visibility = VISIBLE
                    pbLoading.visibility = GONE
                }
            }
        }
    }

    private fun setupToolbar() {
        binding.toolbarListGenre.apply {
            setSupportActionBar(toolbar())
            setTitle(getString(R.string.lbl_genre))
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            supportActionBar?.title = ""
            supportActionBar?.setHomeButtonEnabled(false)
        }
    }
}