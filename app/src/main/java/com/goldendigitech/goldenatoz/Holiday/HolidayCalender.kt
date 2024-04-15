package com.goldendigitech.goldenatoz.Holiday

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.goldendigitech.goldenatoz.MainActivity
import com.goldendigitech.goldenatoz.MyProfile.MyProfile
import com.goldendigitech.goldenatoz.databinding.ActivityHolidayCalenderBinding
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HolidayCalender : AppCompatActivity() {

    lateinit var holidayCalenderBinding: ActivityHolidayCalenderBinding
    private lateinit var holidayCalenderViewModel: HolidayCalenderViewModel
    private lateinit var holidayCalenderAdapter: HolidayCalenderAdapter
    private val holidayModelList = ArrayList<Holiday>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        holidayCalenderBinding = ActivityHolidayCalenderBinding.inflate(layoutInflater)
        val view: View = holidayCalenderBinding.root
        setContentView(view)

        holidayCalenderViewModel = ViewModelProvider(this).get(HolidayCalenderViewModel::class.java)

        // Initialize RecyclerView and adapter
        holidayCalenderBinding.rvHoliday.layoutManager = LinearLayoutManager(this)
        holidayCalenderAdapter = HolidayCalenderAdapter(this, holidayModelList)
        holidayCalenderBinding.rvHoliday.adapter = holidayCalenderAdapter

        // Set up MaterialCalendarView
        setupCalendarView()

        // Fetch holiday data
        holidayCalenderViewModel.holidayCalenderLiveData.observe(this, Observer { holidayResponse ->
            holidayResponse?.let { response ->
                val holidayModelList = response

                Log.d("APIResponse", "Holiday List: $holidayModelList")
                // Update the RecyclerView
                holidayCalenderAdapter.updateData(holidayModelList)
                // Decorate the calendar with red dots for holidays and Sundays
                updateCalendarWithHolidays(holidayModelList)

                // Set the default selected date to trigger onDateSelected callback
                val defaultDate = CalendarDay.today()
                handleDateSelection(defaultDate, holidayModelList)

                // Set up the date selection listener
                holidayCalenderBinding.calender.setOnDateChangedListener { widget, date, selected ->
                    // Handle date selection
                    handleDateSelection(date, holidayModelList)
                }
            }
        })
        holidayCalenderViewModel.fetchHolidayCalendar()

        // Set up the back button click listener
        holidayCalenderBinding.ivBack.setOnClickListener {
            val i: Intent = Intent(this@HolidayCalender, MainActivity::class.java)
            startActivity(i)
            finishAffinity()
        }

    }

    private fun setupCalendarView() {
        // Set the selected date
        holidayCalenderBinding.calender.setSelectedDate(CalendarDay.today())

        // Set the range of selectable dates
        holidayCalenderBinding.calender.state().edit()
            .setMinimumDate(CalendarDay.from(2021, 1, 1))
            .setMaximumDate(CalendarDay.from(2024, 12, 31))
            .commit()
        // Set up the month changed listener
        holidayCalenderBinding.calender.setOnMonthChangedListener(object : OnMonthChangedListener {
            override fun onMonthChanged(widget: MaterialCalendarView, date: CalendarDay) {
                // Update the RecyclerView with data for the selected month
                updateRecyclerViewForMonth(date)
            }
        })

        // Set up the date selection listener
        holidayCalenderBinding.calender.setOnDateChangedListener(object : OnDateSelectedListener {
            override fun onDateSelected(widget: MaterialCalendarView, date: CalendarDay, selected: Boolean) {
                // Handle the date selection by passing holidayModelList
                handleDateSelection(date, holidayModelList)
            }
        })
    }

    private fun updateRecyclerViewForMonth(date: CalendarDay) {
        // Extract the year and month from the selected date
        val year = date.year
        val month = date.month + 1 // Months are zero-based, so add 1

        // Filter the holidayModelList to include only data for the selected month
        val filteredList = holidayModelList.filter { holiday ->
            val holidayDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(holiday.hDate)
            val calendar = Calendar.getInstance().apply { time = holidayDate }
            calendar[Calendar.YEAR] == year && calendar[Calendar.MONTH] + 1 == month
        }

        // Update the RecyclerView with data for the selected month
        holidayCalenderAdapter.updateData(filteredList)
    }

    private fun handleDateSelection(date: CalendarDay, holidayModelList: List<Holiday>) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val selectedDate = dateFormat.format(date.date)

        val selectedDateHolidays = mutableListOf<Holiday>()

        for (holiday in holidayModelList) {
            // Use a common date format for comparison without time
            val holidayDate = holiday.hDate.substring(0, 10)

            if (holidayDate == selectedDate) {
                selectedDateHolidays.add(holiday)
            }
        }

        // Update the RecyclerView with data for the selected date
        holidayCalenderAdapter.updateData(selectedDateHolidays)

        if (selectedDateHolidays.isNotEmpty()) {
            // Display date and name for the selected date
            val firstHoliday = selectedDateHolidays[0]
            val message = "Date: ${firstHoliday.hDate}\nName: ${firstHoliday.name}"
            // Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
        else {
            // Optionally, display a message if there are no holidays for the selected date
            Toast.makeText(this, "No holidays for selected date", Toast.LENGTH_SHORT).show()
        }
    }



    private fun updateCalendarWithHolidays(holidays: List<Holiday>) {
        // Clear existing decorators
        holidayCalenderBinding.calender.removeDecorators()

        // Decorate Sundays with red color
        holidayCalenderBinding.calender.addDecorator(SundayDecorator())

        // Decorate holidays with red dots
        holidayCalenderBinding.calender.addDecorator(EventDecorator(holidays))
    }

}