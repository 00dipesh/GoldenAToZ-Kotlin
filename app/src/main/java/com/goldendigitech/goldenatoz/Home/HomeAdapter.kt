package com.goldendigitech.goldenatoz.Home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.goldendigitech.goldenatoz.R

class HomeAdapter(
    val context: Context,
    val list: List<HomeModel>,
    val listner: OnItemClickListener
) :
    RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.home_men_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val hm = list[position]
        holder.bind(hm)
        holder.itemView.setOnClickListener {
            listner.onItemClick(hm)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val iv_menuimg: ImageView = itemView.findViewById(R.id.iv_menuimg)
        private val tv_name: TextView = itemView.findViewById(R.id.tv_name)

        fun bind(item: HomeModel) {
            iv_menuimg.setImageResource(item.image)
            tv_name.text = item.name
        }
    }

    interface OnItemClickListener {
        fun onItemClick(item: HomeModel)
    }
}