package com.example.movieapi.api

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.example.movieapi.models.Category
import com.example.movieapi.models.Movie
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.*

class API(private var context: Context) {
    private val mapper = ObjectMapper()

    companion object { // Singleton Design Patterns
        @SuppressLint("StaticFieldLeak")
        private var instance: API? = null
        fun getInstance(context: Context): API {
            if (instance == null) return API(context)
            return instance as API
        }
    }

    fun getMovieCategories(): ArrayList<Category> {
        val categories = ArrayList<Category>()
        val jsonString = readJSonFile()
        val movieJsonNode: JsonNode = this.mapper.readTree(jsonString)

        for (category in movieJsonNode.findValue("categories")) {
            val categoryName = category.findValue("name").asText().toString()
            categories.add(Category(categoryName))
        }

        return categories
    }

    fun getMovies(categoryName: String): ArrayList<Movie> {
        val movies = ArrayList<Movie>()
        val jsonString = readJSonFile()
        val movieJsonNode: JsonNode = this.mapper.readTree(jsonString)

        // search for movie category
        val categoryIndex = searchForCategory(movieJsonNode, categoryName)

        // check if the category exist or not then get all movies
        if (categoryIndex != -1) {
            for (movie in movieJsonNode.findValue("categories").get(categoryIndex)
                .findValue("movies")) {
                val selectedCategoryName = movieJsonNode.findValue("categories").get(categoryIndex)
                    .findValue("name").asText()
                val movieName = movie.findValue("name").asText().toString()
                val movieDescription = movie.findValue("description").asText().toString()
                movies.add(Movie(movieName, movieDescription, selectedCategoryName))
            }
        }

        return movies
    }

    private fun searchForCategory(movieJsonNode: JsonNode, categoryName: String): Int {
        var categoryIndex = -1
        var indexCounter = 0
        for (cat in movieJsonNode.findValue("categories")) {
            if (cat.findValue("name").asText().toString() == categoryName) {
                categoryIndex = indexCounter
                break
            }
            indexCounter++
        }

        return categoryIndex
    }

    private fun readJSonFile(): String {
        val jsonFile: InputStream = context.assets.open("movies-data.json")
        val reader = BufferedReader(InputStreamReader(jsonFile))
        var line: String?
        var movieString = ""

        while (true) {
            try {
                if (reader.readLine().also { line = it } == null) break
                movieString += line + "\n"

            } catch (e: IOException) {
                Log.d("file", "Error in Json File")
            }
        }

        jsonFile.close()
        return movieString
    }

}