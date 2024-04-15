package com.goldendigitech.goldenatoz.Leave.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.goldendigitech.goldenatoz.Leave.Response.LeaveData
import com.goldendigitech.goldenatoz.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class LeaveAdapter (private val context: Context, private val leaveModelList: MutableList<LeaveData>) :
    RecyclerView.Adapter<LeaveAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.leave_status_item_list, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dol = leaveModelList[position]
        holder.tv_leavetype.text = dol.LeaveType
        holder.tv_leaveperiod.text = dol.LeavePeriod

        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        try {
            val fromDate = inputFormat.parse(dol.FromDate)
            val toDate = inputFormat.parse(dol.ToDate)

            val formattedFromDate = outputFormat.format(fromDate)
            val formattedToDate = outputFormat.format(toDate)
            holder.tv_leaveduration.text = "$formattedFromDate To $formattedToDate"

        } catch (e: ParseException) {
            e.printStackTrace()
        }

        holder.tv_leavereason.text = dol.Remark

        val inputcdFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
        val outputcdFormat = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
        try {
            val createdAt = inputcdFormat.parse(dol.CreatedAt)
            val formattedCreatedAt = outputcdFormat.format(createdAt)
            holder.tv_leaveapply.text = formattedCreatedAt
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return leaveModelList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_leavetype: TextView = itemView.findViewById(R.id.tv_leavetype)
        var tv_leaveperiod: TextView = itemView.findViewById(R.id.tv_leaveperiod)
        var tv_leaveduration: TextView = itemView.findViewById(R.id.tv_leaveduration)
        var tv_leavereason: TextView = itemView.findViewById(R.id.tv_leavereason)
        var tv_leavemanager: TextView = itemView.findViewById(R.id.tv_leavemanager)
        var tv_leaveapply: TextView = itemView.findViewById(R.id.tv_leaveapply)
    }

    fun setLeaveModelList(newLeaveModelList: List<LeaveData>) {
        leaveModelList.clear()
        leaveModelList.addAll(newLeaveModelList)
        notifyDataSetChanged()
    }
}