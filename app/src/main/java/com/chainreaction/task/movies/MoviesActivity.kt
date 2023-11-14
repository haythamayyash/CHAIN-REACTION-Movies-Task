package com.chainreaction.task.movies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.chainreaction.task.movies.databinding.ActivityMoviesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMoviesBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolbar()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        title = getString(R.string.movies)
    }

}