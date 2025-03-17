package com.example.m3_project_praktikum

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MoviesAdapter (
    val data: ArrayList<Movie>,
    val layout: Int,
): RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    class ViewHolder(val row: View) : RecyclerView.ViewHolder(row){
        val ivPhoto: ImageView = row.findViewById(R.id.ivMoviePrev)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(parent.context)
        return ViewHolder(itemView.inflate(
            layout, parent ,false
        ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = data[position]
        holder.ivPhoto.setImageResource(current.poster)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}