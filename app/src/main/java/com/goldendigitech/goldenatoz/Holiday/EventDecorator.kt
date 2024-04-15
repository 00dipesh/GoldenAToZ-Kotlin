package com.goldendigitech.goldenatoz.Holiday

import android.graphics.Color
import android.util.Log
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import java.text.SimpleDateFormat
import java.util.Locale

class EventDecorator (private val holidayModelList: List<Holiday>) : DayViewDecorator {
    override fun shouldDecorate(day: CalendarDay): Boolean {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dateString = sdf.format(day.date.time)

        for (holiday in holidayModelList) {
            val holidayDate = holiday.hDate.substring(0, 10)

            Log.d("EventDecorator", "Checking date: $holidayDate")

            if (holidayDate == dateString) {
                Log.d("EventDecorator", "Decorating date: $dateString")
                return true
            }
        }

        return false
    }

    override fun decorate(view: DayViewFacade) {
        // Decorate holidays with red dots
        view.addSpan(DotSpan(8f, Color.RED))
//        view.addSpan(ForegroundColorSpan(Color.RED))
    }
}