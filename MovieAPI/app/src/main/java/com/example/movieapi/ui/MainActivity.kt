package com.example.movieapi.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapi.api.API
import com.example.movieapi.adapters.CategoryAdapter
import com.example.movieapi.databinding.ActivityMainBinding
import com.example.movieapi.models.Category

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var categories: ArrayList<Category>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showCategories()
        onClickCategoryCard()

    }

    private fun showCategories(){
        val api = API.getInstance(this)
        categories = api.getMovieCategories()
        if(categories.size !=0) {
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
            categoryAdapter = CategoryAdapter(categories, this)
            binding.recyclerView.adapter = categoryAdapter
            binding.label.visibility = View.VISIBLE
            binding.noCategories.visibility = View.GONE
        }
        else{
            binding.label.visibility = View.GONE
            binding.noCategories.visibility = View.VISIBLE
        }
    }

    private fun onClickCategoryCard() {
        categoryAdapter.setOnItemClickListener(object : CategoryAdapter.RecyclerViewClickListener {
            override fun onClick(position: Int) {
                val category: Category = categories[position]
                val intent = Intent(this@MainActivity, MoviesActivity::class.java)
                intent.putExtra("category_name", category.getCategoryName())
                startActivity(intent)
            }
        })
    }

}