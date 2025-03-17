package com.example.m3_project_praktikum

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Visibility

class CinemaAdapter (
    val data: ArrayList<Cinema>,
    val layout: Int,
): RecyclerView.Adapter<CinemaAdapter.ViewHolder>() {

    class ViewHolder(val row: View) : RecyclerView.ViewHolder(row){
        val tvTitle: TextView = row.findViewById(R.id.tvCinemaName)
        var banners: ArrayList<Button> = arrayListOf<Button>(
            row.findViewById(R.id.bannerCinema1),
            row.findViewById(R.id.bannerCinema2),
            row.findViewById(R.id.bannerCinema3)
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(parent.context)
        return ViewHolder(itemView.inflate(
            layout, parent ,false
        ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = data[position]
        holder.tvTitle.setText("${current.title} (${current.distance} km)")
        var length = current.type.size;
        for (i in 0..2) {
            if(i < length){
                holder.banners[i].setText(current.type[i])
                holder.banners[i].visibility = View.VISIBLE
            }else{
                holder.banners[i].visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}