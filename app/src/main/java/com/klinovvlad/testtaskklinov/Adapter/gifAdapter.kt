package com.klinovvlad.testtaskklinov.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.klinovvlad.testtaskklinov.Model.DataObj
import com.klinovvlad.testtaskklinov.R
import com.klinovvlad.testtaskklinov.databinding.ItemMainBinding

class gifAdapter(val context: Context, val dataList: List<DataObj>): RecyclerView.Adapter<gifAdapter.Holder>() {

    class Holder(item: View) : RecyclerView.ViewHolder(item) {
        val binding = ItemMainBinding.bind(item)
        val image: ImageView
        init {
            image = binding.imageView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_main, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val data = dataList[position]
        Glide.with(context)
            .load(data._images._originalImage.url)
            .into(holder.image)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}