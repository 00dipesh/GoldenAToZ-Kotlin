package com.goldendigitech.goldenatoz.attendance

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goldendigitech.goldenatoz.serverConnect.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TaskListViewModel : ViewModel() {

    private val webServices = Constant.webService

    private val _tasklistResponse = MutableLiveData<TaskResponse>()
    val tasklistResponse: LiveData<TaskResponse> = _tasklistResponse


    fun showTaskList() {
        webServices.getTaskList().enqueue(object : Callback<TaskResponse> {
            override fun onResponse(call: Call<TaskResponse>, response: Response<TaskResponse>) {
                if (response.isSuccessful) {
                    _tasklistResponse.value = response.body()
                    Log.e("TaskListViewModel", "Task List Success: ${_tasklistResponse.value}")
                } else {
                    Log.e("TaskListViewModel", "Task List failed: ${response.code()}")
                }

            }

            override fun onFailure(call: Call<TaskResponse>, t: Throwable) {
                Log.e("TaskListViewModel", "Network error: ${t.message}")

            }
        })
    }

}