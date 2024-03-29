package com.goldendigitech.goldenatoz.attendance

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goldendigitech.goldenatoz.serverConnect.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AttendanceViewModel : ViewModel() {
    private val webServices = Constant.webService

    private val _attendanceResponse = MutableLiveData<AttendanceResponse>()
    val attendanceResponse: LiveData<AttendanceResponse> = _attendanceResponse

    fun addAttendance(attendanceRequest: AttendanceModel) {
       webServices.addAttendance(attendanceRequest).enqueue(object : Callback<AttendanceResponse>
       {
           override fun onResponse(call: Call<AttendanceResponse>, response: Response<AttendanceResponse>)
           {
               if (response.isSuccessful) {
                   _attendanceResponse.value = response.body()
                   Log.e("AttendanceViewModel", "Attendance Success: ${_attendanceResponse.value}")
               }
              else
               {
                   Log.e("AttendanceViewModel", "Attendance failed: ${response.code()}")
               }
           }

           override fun onFailure(call: Call<AttendanceResponse>, t: Throwable) {
               Log.e("AttendanceViewModel", "Network error: ${t.message}")

           }
       })
    }



    private val _workingwithsjResponse = MutableLiveData<WorkingWithResponse>()
    val workingwithsjResponse: LiveData<WorkingWithResponse> = _workingwithsjResponse

    fun handleWorkingWith()
    {
        webServices.getWorkingWithModel().enqueue(object : Callback<WorkingWithResponse>
        {
            override fun onResponse(
                call: Call<WorkingWithResponse>,
                response: Response<WorkingWithResponse>)
            {
                if (response.isSuccessful) {
                    _workingwithsjResponse.value = response.body()
                    Log.e("AttendanceViewModel", "workingwith Success: ${_workingwithsjResponse.value}")
                }
                else
                {
                    Log.e("AttendanceViewModel", "workingwith failed: ${response.code()}")
                }

            }

            override fun onFailure(call: Call<WorkingWithResponse>, t: Throwable) {
                Log.e("AttendanceViewModel", "Network error: ${t.message}")


            }
        })
    }
}
