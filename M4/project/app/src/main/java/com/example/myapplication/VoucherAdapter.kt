package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Voucher

class VoucherAdapter(
    var items: MutableList<Voucher>,
) :RecyclerView.Adapter<VoucherAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val desc: TextView = itemView.findViewById(R.id.tvDesc);
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.voucher_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (items[position].tipe == "nominal") {
            holder.desc.text = "Voucher potongan ${items[position].potongan}K min pembelian ${items[position].minimum}K"
        }else if(items[position].tipe=="persentase"){
            holder.desc.text = "Voucher potongan ${items[position].potongan}% min pembelian ${items[position].minimum}K"
        }
    }

    override fun getItemCount(): Int = items.size
}