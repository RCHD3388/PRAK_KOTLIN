package com.example.projectm5

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

class DataAdapter(
    var listData: ArrayList<Transaction>
):RecyclerView.Adapter<SkillViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.transaction_item, parent, false)
        return SkillViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: SkillViewHolder, position: Int) {
        val data = listData[position]
        holder.tvType.text = data.category
        holder.tvNote.text = data.note
        holder.tvAmount.text = "Rp ${NumberFormat.getNumberInstance(Locale("in", "ID")).format(data.amount)}"

        if (data.type == "pengeluaran") {
            holder.tvAmount.setTextColor(ContextCompat.getColor(holder.tvAmount.context, R.color.danger))
        } else {
            holder.tvAmount.setTextColor(ContextCompat.getColor(holder.tvAmount.context, R.color.success)) // Asumsi Anda punya R.color.income
        }

        val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
        holder.tvTime.text = sdf.format(data.date.time)

        when(data.category){
            "Allowance" -> holder.ivImage.setImageResource(R.drawable.allowance)
            "Salary" -> holder.ivImage.setImageResource(R.drawable.salary)
            "Bonus" -> holder.ivImage.setImageResource(R.drawable.bonus)
            "Food and Beverage" -> holder.ivImage.setImageResource(R.drawable.foodbeverage)
            "Transportation" -> holder.ivImage.setImageResource(R.drawable.transportation)
            "Shopping" -> holder.ivImage.setImageResource(R.drawable.shopping)
            "Cinema" -> holder.ivImage.setImageResource(R.drawable.cinema)
            "Health" -> holder.ivImage.setImageResource(R.drawable.health)
            else -> holder.ivImage.setImageResource(R.drawable.other)
        }
    }
}

class SkillViewHolder(val row: View):RecyclerView.ViewHolder(row){
    val ivImage = row.findViewById<ImageView>(R.id.ivImage)
    val tvType = row.findViewById<TextView>(R.id.tvType)
    val tvNote = row.findViewById<TextView>(R.id.tvNote)
    val tvAmount = row.findViewById<TextView>(R.id.tvAmount)
    val tvTime = row.findViewById<TextView>(R.id.tvTime)
}