package com.example.movieapi.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapi.R
import com.example.movieapi.adapters.CategoryAdapter
import com.example.movieapi.api.API
import com.example.movieapi.databinding.ActivityMainBinding
import com.example.movieapi.models.Category
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var categories: ArrayList<Category>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showCategories()
        onClickCategoryCard()
        bottomNavigationView = binding.bottomNav
        bottomNavigationView.setOnNavigationItemSelectedListener(naveListener)
    }

    private val naveListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.add_icon_id -> {
                    Toast.makeText(this, getString(R.string.add_movie), Toast.LENGTH_SHORT).show()
                }
                R.id.update_icon_id -> {
                    Toast.makeText(this, getString(R.string.update_movie), Toast.LENGTH_SHORT)
                        .show()
                }
                R.id.delete_icon_id -> {
                    Toast.makeText(this, getString(R.string.delete_movie), Toast.LENGTH_SHORT)
                        .show()
                }
            }
            true
        }


    private fun showCategories() {
        val api = API.getInstance(this)
        try {
            categories = api.getMovieCategories()
            if (categories.size != 0) {
                binding.recyclerView.layoutManager = LinearLayoutManager(this)
                categoryAdapter = CategoryAdapter(categories, this)
                binding.recyclerView.adapter = categoryAdapter
                binding.noCategories.visibility = View.GONE
            } else {
                binding.noCategories.visibility = View.VISIBLE
            }
        } catch (e: Exception) {
            Toast.makeText(this, getString(R.string.exception_message), Toast.LENGTH_SHORT).show()
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