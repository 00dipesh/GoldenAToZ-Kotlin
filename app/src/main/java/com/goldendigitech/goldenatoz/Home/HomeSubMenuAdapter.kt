package com.goldendigitech.goldenatoz.Home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.goldendigitech.goldenatoz.R

class HomeSubMenuAdapter(val context: Context,val list: List<HomeSubMenuModel>) :
RecyclerView.Adapter<HomeSubMenuAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.home_submenu_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HomeSubMenuAdapter.MyViewHolder, position: Int) {
        val hsm=list[position]
        holder.tvName.text =hsm.name
        holder.tvqtv.text = hsm.qty

    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName :TextView = itemView.findViewById(R.id.tv_name)
        val tvqtv : TextView =itemView.findViewById(R.id.tv_qty)
    }


}