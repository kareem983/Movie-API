package com.example.movieapi.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapi.R
import com.example.movieapi.models.Category


class CategoryAdapter(private val categoriesArrayList: ArrayList<Category>, private val context: Context)
    : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private lateinit var listener: RecyclerViewClickListener
    fun setOnItemClickListener(listener: RecyclerViewClickListener){
        this.listener = listener
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(
            R.layout.custom_category_card, parent
            , false), this.listener)
    }

    override fun getItemCount(): Int {
        return categoriesArrayList.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category: Category = categoriesArrayList[position]
        holder.categoryName.text = category.getCategoryName()
    }


    class ViewHolder(itemView: View, listener: RecyclerViewClickListener) : RecyclerView.ViewHolder(itemView) {
        val categoryName: TextView = itemView.findViewById(R.id.category_name)
        init {
            itemView.setOnClickListener{ listener.onClick(adapterPosition) }
        }
    }

    interface RecyclerViewClickListener {
        fun onClick(position: Int)
    }

}