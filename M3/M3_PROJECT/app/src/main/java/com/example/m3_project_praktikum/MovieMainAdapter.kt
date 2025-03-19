package com.example.m3_project_praktikum

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MovieMainAdapter (
    val data: ArrayList<Movie>,
    val layout: Int,
    val listener: (Movie) -> Unit
): RecyclerView.Adapter<MovieMainAdapter.ViewHolder>() {

    class ViewHolder(val row: View) : RecyclerView.ViewHolder(row){
        val tvTitle = row.findViewById<TextView>(R.id.tvMovieName)
        val tvDetail = row.findViewById<TextView>(R.id.tvDetail)
        val bannerDuration = row.findViewById<TextView>(R.id.bannerDuration)
        val bannerAge = row.findViewById<TextView>(R.id.bannerAge)
        val tvRating = row.findViewById<TextView>(R.id.tvRating)
        val ivPoster = row.findViewById<ImageView>(R.id.ivPoster)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(parent.context)
        return ViewHolder(itemView.inflate(
            layout, parent ,false
        ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = data[position]
        holder.tvTitle.setText(current.title)
        holder.bannerAge.setText(current.agerating)
        holder.tvDetail.setText(current.genre)
        holder.bannerDuration.setText("${current.hour}h ${current.minute}m")
        holder.tvRating.setText("${current.rating.toString()}/5")
        holder.ivPoster.setImageResource(current.poster)

        holder.row.setOnClickListener{
            listener.invoke(current)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}