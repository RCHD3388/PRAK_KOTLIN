package com.example.m3_project_praktikum

import android.provider.ContactsContract.Data
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MoviePosterAdapter (
    val data: ArrayList<MoviePoster>,
    val layout: Int,
    var onClickListener: (MoviePoster) -> Unit
): RecyclerView.Adapter<MoviePosterAdapter.ViewHolder>() {

    class ViewHolder(val row: View) : RecyclerView.ViewHolder(row){
        val tvPoster = row.findViewById<ImageView>(R.id.ivPoster)
        val radioButton = row.findViewById<RadioButton>(R.id.radioButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(parent.context)
        return ViewHolder(itemView.inflate(
            layout, parent ,false
        ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = data[position]
        holder.tvPoster.setImageResource(current.poster)
        holder.radioButton.setText(current.label)
        holder.radioButton.isChecked = current.checked

        holder.radioButton.setOnClickListener {
            onClickListener.invoke(current)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}