package com.example.movieapi.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapi.R
import com.example.movieapi.models.Movie

class MovieAdapter(private val moviesArrayList: ArrayList<Movie>, private val context: Context)
    : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.custom_movie_card, parent
            , false))
    }

    override fun getItemCount(): Int {
        return moviesArrayList.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie: Movie = moviesArrayList[position]
        holder.movieName.text = movie.getMovieName()
        holder.movieDescription.text = movie.getMovieDescription()
    }


    class ViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView) {
        val movieName: TextView = itemView.findViewById(R.id.movie_name)
        val movieDescription: TextView = itemView.findViewById(R.id.movie_description)

    }

}