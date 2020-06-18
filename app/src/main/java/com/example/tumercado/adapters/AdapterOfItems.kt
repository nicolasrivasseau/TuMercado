package com.example.tumercado.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.example.tumercado.R
import com.example.tumercado.entity.searchforqueary.Result

import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.item_result.view.*
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class AdapterOfItems(val onDetailsClick: (item: Result) ->Unit):
    RecyclerView.Adapter<AdapterOfItems.ViewHolder>() {
    var itemsList = ArrayList<Result>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_result, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        itemsList[position].let { item ->
            holder.itemView.title.text = item.title
            val nformat: NumberFormat = DecimalFormat("##,###,###.##")
            holder.itemView.precio.text = "$ " + nformat.format(item.price)



            Picasso.get()
                .load(item.thumbnail.replace("http", "https").toString())
                .placeholder(R.drawable.loading_spinner)
                .into(holder.itemView.imageViewProduct)


            holder.itemView.setOnClickListener {
                onDetailsClick(item)
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){}

}