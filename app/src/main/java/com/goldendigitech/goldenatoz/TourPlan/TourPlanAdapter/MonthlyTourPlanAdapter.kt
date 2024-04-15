package com.goldendigitech.goldenatoz.TourPlan.TourPlanAdapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.goldendigitech.goldenatoz.R
import com.goldendigitech.goldenatoz.TourPlan.TourPlanModel.MonthlyTourModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MonthlyTourPlanAdapter (val context: Context,val monthlyTourPlanList: List<MonthlyTourModel>) :
RecyclerView.Adapter<MonthlyTourPlanAdapter.MyTourCalendar> () {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MonthlyTourPlanAdapter.MyTourCalendar {
        val view = LayoutInflater.from(context).inflate(R.layout.tour_list, parent, false)
        return MyTourCalendar(view)
    }

    override fun onBindViewHolder(holder: MonthlyTourPlanAdapter.MyTourCalendar, position: Int) {
        val mtm = monthlyTourPlanList[position]

        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

       try {
            val date: Date = inputFormat.parse(mtm.Date)
            val formattedDate: String = outputFormat.format(date)
            holder.tv_date.text = formattedDate
        } catch (e: ParseException) {
            throw RuntimeException(e)
        }

        holder.tv_state.text = mtm.State
        holder.tv_ss.text = mtm.SsName
        holder.tv_taskname.text = mtm.TaskOfDay
        holder.tv_town.text = mtm.Town
        holder.tv_beatname.text = mtm.BeatName
        holder.tv_distributor.text = mtm.DistributorName
        Log.d("TAG", "response : $mtm.State")
        Log.d("TAG", "response : $mtm.SsName")
        Log.d("TAG", "response : $mtm.TaskOfDay")
        Log.d("TAG", "response : $mtm.Town")
        Log.d("TAG", "response : $mtm.BeatName")
        Log.d("TAG", "response : $mtm.mtm.DistributorName")


    }

    override fun getItemCount(): Int {
        return monthlyTourPlanList.size
    }

    inner class MyTourCalendar(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_date: TextView = itemView.findViewById(R.id.tv_date)
        var tv_state: TextView = itemView.findViewById(R.id.tv_state)
        var tv_ss: TextView = itemView.findViewById(R.id.tv_ss)
        var tv_taskname: TextView = itemView.findViewById(R.id.tv_taskname)
        var tv_town: TextView = itemView.findViewById(R.id.tv_town)
        var tv_beatname: TextView = itemView.findViewById(R.id.tv_beatname)
        var tv_distributor: TextView = itemView.findViewById(R.id.tv_distributor)
    }

}