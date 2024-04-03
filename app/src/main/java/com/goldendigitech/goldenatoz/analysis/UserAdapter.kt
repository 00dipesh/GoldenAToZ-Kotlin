package com.goldendigitech.goldenatoz.analysis

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.goldendigitech.goldenatoz.R

class UserAdapter (val context: Context,val userModelList: List<UserModel>) : RecyclerView.Adapter<UserAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_item_list, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserAdapter.MyViewHolder, position: Int) {
        val um = userModelList[position]
        holder.tv_teamsize.text = um.teamSize
        holder.tv_attendance.text = um.attendance
        holder.tv_bccount.text = um.beatcoveragecount
        holder.tv_totalcall.text = um.totalcall
        holder.tv_productivecall.text = um.qty
    }

    override fun getItemCount(): Int {
        return userModelList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_teamsize: TextView = itemView.findViewById(R.id.tv_teamsize)
        var tv_attendance: TextView = itemView.findViewById(R.id.tv_attendance)
        var tv_bccount: TextView = itemView.findViewById(R.id.tv_bccount)
        var tv_totalcall: TextView = itemView.findViewById(R.id.tv_totalcall)
        var tv_productivecall: TextView = itemView.findViewById(R.id.tv_productivecall)
    }

}