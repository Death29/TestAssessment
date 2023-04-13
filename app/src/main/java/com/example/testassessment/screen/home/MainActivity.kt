package com.example.testassessment.screen.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.example.testassessment.databinding.ActivityMainBinding
import com.example.testassessment.screen.genre.ActivityListGenre
import com.example.testassessment.util.ViewModelMovie
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModelMovie: ViewModelMovie by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        binding.apply {
            viewModelMovie.loadGuestSession()

            viewModelMovie.guestSession.observe(this@MainActivity){
                startActivity(Intent(this@MainActivity, ActivityListGenre::class.java))
                finish()
            }

            viewModelMovie.loading.observe(this@MainActivity){
                setConditionLoading(it)
            }
        }
    }

    private fun setConditionLoading(state: Boolean){
        binding.apply {
            if (state){
                tvTmdb.visibility = View.GONE
                pbLoading.visibility = View.VISIBLE
            }else{
                tvTmdb.visibility = View.VISIBLE
                pbLoading.visibility = View.GONE
            }
        }
    }
}