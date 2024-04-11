package com.goldendigitech.goldenatoz.TourPlan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.goldendigitech.goldenatoz.MainActivity
import com.goldendigitech.goldenatoz.R
import com.goldendigitech.goldenatoz.TourPlan.TourPlanAdapter.MonthlyTourPlanAdapter
import com.goldendigitech.goldenatoz.TourPlan.TourPlanModel.GetAllTourResponce
import com.goldendigitech.goldenatoz.databinding.ActivityChangepasswordBinding
import com.goldendigitech.goldenatoz.databinding.ActivitySelectTourPlanBinding
import com.goldendigitech.goldenatoz.serverConnect.Constant
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class SelectTourPlanActivity : AppCompatActivity() {

    val TAG = "SelectTourPlanActivity"
    lateinit var selectTourPlanBinding: ActivitySelectTourPlanBinding
    private lateinit var monthlyTourPlanAdapter: MonthlyTourPlanAdapter
    private var eventDates: MutableList<Calendar>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  setContentView(R.layout.activity_select_tour_plan)
        selectTourPlanBinding = ActivitySelectTourPlanBinding.inflate(layoutInflater)
        val view: View = selectTourPlanBinding.root
        setContentView(view)
        handleAllTourList();

        selectTourPlanBinding.ivBack.setOnClickListener {
            val intent = Intent(this@SelectTourPlanActivity, MainActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }


        selectTourPlanBinding.calender.setSelectedDate(CalendarDay.today())
        selectTourPlanBinding.calender.state().edit()
            .setMinimumDate(CalendarDay.from(2021, 1, 1))
            .setMaximumDate(CalendarDay.from(2024, 12, 31))
            .commit()

        selectTourPlanBinding.calender.setOnMonthChangedListener(object : OnMonthChangedListener {
            override fun onMonthChanged(widget: MaterialCalendarView?, date: CalendarDay?) {

            }

        })

        selectTourPlanBinding.calender.setOnDateChangedListener(object : OnDateSelectedListener {
            override fun onDateSelected(
                widget: MaterialCalendarView,
                date: CalendarDay,
                selected: Boolean
            ) {
                val selectedDate = date.date
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val selectedDateString = dateFormat.format(selectedDate)
                val dayFormat = SimpleDateFormat("EEEE", Locale.getDefault())
                val selectedDay = dayFormat.format(selectedDate)

                val hasTourPlan = hasTourPlanForDate(selectedDateString)
                if (hasTourPlan) {
                    showToast("Tour plan already available for selected date")
                } else {
                    val intent = Intent(this@SelectTourPlanActivity, TourPlanActivity::class.java)
                    intent.putExtra("selected_date", selectedDateString)
                    intent.putExtra("selected_day", selectedDay)
                    startActivity(intent)
                }


            }
        })


    }

    private fun handleAllTourList() {

        val allTourCall: Call<GetAllTourResponce> = Constant.webService.getAllTourPlanList()
        allTourCall.enqueue(object : Callback<GetAllTourResponce> {
            override fun onResponse(
                call: Call<GetAllTourResponce>,
                response: Response<GetAllTourResponce>
            ) {
                if (response.isSuccessful) {
                    val allTourResponce = response.body()
                    val monthlyTourModelList = allTourResponce?.data
                    if (monthlyTourModelList != null) {
                        selectTourPlanBinding.rvTour.layoutManager =
                            LinearLayoutManager(this@SelectTourPlanActivity)
                        monthlyTourPlanAdapter = MonthlyTourPlanAdapter(
                            this@SelectTourPlanActivity,
                            monthlyTourModelList
                        )
                        selectTourPlanBinding.rvTour.adapter = monthlyTourPlanAdapter

                        eventDates = ArrayList()
                        for (tourModel in monthlyTourModelList) {
                            try {
                                val eventDate = parseDate(tourModel.Date.toString())
                                eventDates?.add(eventDate)
                            } catch (e: ParseException) {
                                throw RuntimeException(e)
                            }
                        }
                        // Update calendar view with event dates
                        selectTourPlanBinding.calender.addDecorator(TourDecorartor(eventDates!!))
                    }
                } else {
                    showToast("Internal Server Error")
                }

            }

            override fun onFailure(call: Call<GetAllTourResponce>, t: Throwable) {
                showToast("Network Failure")
            }


        })
    }

    private fun parseDate(dateString: String): Calendar {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val date = sdf.parse(dateString)
        val cal = Calendar.getInstance()
        cal.time = date
        return cal
    }

    private fun hasTourPlanForDate(selectedDate: String): Boolean {
        eventDates?.let {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            for (cal in it) {
                val dateStr = sdf.format(cal.time)
                if (dateStr == selectedDate) {
                    return true
                }
            }
        }
        return false
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}