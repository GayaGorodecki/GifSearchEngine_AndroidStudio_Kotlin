package com.example.gifsearchengine.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.gifsearchengine.R
import com.example.gifsearchengine.model.CategoriesList
import com.example.gifsearchengine.view.mainActivity

class CategoriesCustomAdapter(private val values: CategoriesList, private val context: Context) :
    RecyclerView.Adapter<CategoriesCustomAdapter.MyViewHolder>() {

    override fun getItemCount() = values.categories.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.category_card, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        holder.textViewCategory?.text = values.categories[position].name

        holder.textViewCategory?.setOnClickListener(View.OnClickListener { View ->
            // TODO: 22/10/2021 search category gifs
        })
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewCategory: TextView? = null

        init {
            textViewCategory = itemView.findViewById(R.id.textViewCategory)
        }
    }
}