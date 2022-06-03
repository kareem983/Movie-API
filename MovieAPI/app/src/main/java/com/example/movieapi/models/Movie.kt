package com.example.movieapi.models

class Movie(private var name: String, private var description: String, categoryName: String)
    : Category(categoryName)
{

    fun getMovieName():String{
        return this.name
    }
    fun getMovieDescription():String{
        return this.description
    }


}