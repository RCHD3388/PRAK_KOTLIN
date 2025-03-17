package com.example.m3_project_praktikum

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CategoryAdapter (
    val data: ArrayList<Category>,
    val layout: Int,
): RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    class ViewHolder(val row: View) : RecyclerView.ViewHolder(row){
        val tvTitle: TextView = row.findViewById(R.id.tvPhotoTitle)
        val ivPhoto: ImageView = row.findViewById(R.id.ivPhoto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(parent.context)
        return ViewHolder(itemView.inflate(
            layout, parent ,false
        ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = data[position]
        holder.ivPhoto.setImageResource(current.photo)
        holder.tvTitle.setText(current.title)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}