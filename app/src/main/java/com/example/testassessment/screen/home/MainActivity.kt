package com.example.testassessment.screen.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.viewModels
import com.example.testassessment.databinding.ActivityMainBinding
import com.example.testassessment.screen.genre.ActivityListGenre
import com.example.testassessment.util.ViewModelMovie
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        binding.apply {
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this@MainActivity, ActivityListGenre::class.java))
                finish()
            }, 3000)
        }
    }
}