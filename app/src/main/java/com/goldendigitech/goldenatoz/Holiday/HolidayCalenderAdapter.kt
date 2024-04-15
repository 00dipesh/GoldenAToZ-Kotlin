package com.goldendigitech.goldenatoz.Holiday

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.goldendigitech.goldenatoz.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class HolidayCalenderAdapter (private val context: Context, private var holidayModelList: List<Holiday>) :
    RecyclerView.Adapter<HolidayCalenderAdapter.MyHolidayCalendar>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolidayCalendar {
        val view = LayoutInflater.from(context).inflate(R.layout.item_holiday_calender, parent, false)
        return MyHolidayCalendar(view)
    }

    override fun onBindViewHolder(holder: MyHolidayCalendar, position: Int) {
        val hm = holidayModelList[position]
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("MMM dd", Locale.getDefault())
        try {
            val date = inputFormat.parse(hm.hDate)
            val formattedDate = outputFormat.format(date)
            holder.mDate.text = formattedDate
        } catch (e: ParseException) {
            e.printStackTrace()
            holder.mDate.text = ""
        }
        holder.mEvent.text = hm.name
    }

    override fun getItemCount(): Int {
        return holidayModelList.size
    }

    fun updateData(newHolidayList: List<Holiday>) {
        holidayModelList = newHolidayList
        notifyDataSetChanged()
    }

    fun clearData() {
        holidayModelList = emptyList()
        notifyDataSetChanged()
    }
    inner class MyHolidayCalendar(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mDate: TextView = itemView.findViewById(R.id.date)
        val mEvent: TextView = itemView.findViewById(R.id.event)
    }
}