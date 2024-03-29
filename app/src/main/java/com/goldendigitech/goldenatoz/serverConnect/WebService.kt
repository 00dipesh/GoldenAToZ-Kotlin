package com.goldendigitech.goldenatoz.serverConnect

import com.goldendigitech.goldenatoz.attendance.AttendanceModel
import com.goldendigitech.goldenatoz.attendance.AttendanceResponse
import com.goldendigitech.goldenatoz.attendance.TaskResponse
import com.goldendigitech.goldenatoz.attendance.WorkingWithResponse
import com.goldendigitech.goldenatoz.employee.EmployeeResponse
import com.goldendigitech.goldenatoz.login.LoginModel
import com.goldendigitech.goldenatoz.login.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface WebService {


    @POST("Api/Employee/Login")   // Login
    fun userLogin(@Body loginModel: LoginModel): Call<LoginResponse>

    @GET("Api/Employee/Get")  // Employee Data Get
    fun getEmployee(@Query("employeeId") employeeId:Int): Call<EmployeeResponse>

    @GET("Api/Employee/GetIdAndName") //Senior & junior List
    fun getWorkingWithModel(): Call<WorkingWithResponse>

    @POST("Api/Employee/Attendace/Add")  // Add Attendance
    fun addAttendance(@Body requestAddendance: AttendanceModel): Call<AttendanceResponse>

    @GET("Api/Account/DailyTask/GetTaskIdAndNameDict")   //Task List
    fun getTaskList(): Call<TaskResponse>



}