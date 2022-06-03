package com.example.movieapi.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapi.api.API
import com.example.movieapi.adapters.MovieAdapter
import com.example.movieapi.databinding.ActivityMoviesBinding
import com.example.movieapi.models.Movie

class MoviesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMoviesBinding
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var movies: ArrayList<Movie>
    private lateinit var categoryName: String
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        categoryName = intent.extras?.getString("category_name")!!
        binding.label.text = "$categoryName Category"

        showMovies()

    }

    private fun showMovies() {
        val api = API.getInstance(this)
        movies = api.getMovies(categoryName)
        if(movies.size !=0) {
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
            movieAdapter = MovieAdapter(movies, this)
            binding.recyclerView.adapter = movieAdapter
            binding.label.visibility = View.VISIBLE
            binding.noMovies.visibility = View.GONE
        }
        else {
            binding.label.visibility = View.GONE
            binding.noMovies.visibility = View.VISIBLE
        }
    }

}