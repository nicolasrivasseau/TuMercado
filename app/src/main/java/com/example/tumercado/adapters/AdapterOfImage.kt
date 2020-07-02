package com.example.tumercado.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tumercado.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.image_product.view.*

import java.util.*

class AdapterOfImage() :
    RecyclerView.Adapter<AdapterOfImage.ViewHolder>() {
    var image = ArrayList<com.example.tumercado.entity.searchforid.Picture>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.image_product, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return image.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (!image.isEmpty()) {
            image[position].let { item ->
                Picasso.get()
                    .load(item.secure_url)
                    .fit()
                    .placeholder(R.drawable.loading_spinner_details)
                    .into(holder.itemView.imageProduct)
            }/*}else{//agregar imagen vacia!
           Picasso.get()
               .load()
               .fit()
               .placeholder(R.drawable.loading_spinner_details)
               .into(holder.itemView.imageProduct)
           }*/

        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {}

}