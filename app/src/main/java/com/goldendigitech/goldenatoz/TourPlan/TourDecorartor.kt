package com.goldendigitech.goldenatoz.TourPlan

import android.graphics.Color
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan
import java.util.*

class TourDecorartor(private val eventDates: List<Calendar>) : DayViewDecorator {

    override fun shouldDecorate(day: CalendarDay): Boolean {
        val calendar = day.calendar
        eventDates.forEach { eventDate ->
            if (calendar.get(Calendar.YEAR) == eventDate.get(Calendar.YEAR) &&
                calendar.get(Calendar.MONTH) == eventDate.get(Calendar.MONTH) &&
                calendar.get(Calendar.DAY_OF_MONTH) == eventDate.get(Calendar.DAY_OF_MONTH)) {
                return true
            }
        }
        return false
    }

    override fun decorate(view: DayViewFacade) {
        view.addSpan(DotSpan(8F, Color.BLUE))
    }
}