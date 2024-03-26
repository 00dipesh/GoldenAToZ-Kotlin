package com.goldendigitech.goldenatoz.serverConnect


import com.goldendigitech.goldenatoz.attendance.AttendanceModel
import com.goldendigitech.goldenatoz.attendance.AttendanceResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

import com.goldendigitech.goldenatoz.login.LoginModel
import com.goldendigitech.goldenatoz.login.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface WebService {

    @POST("Api/Employee/Login")
    fun userLogin(@Body loginModel: LoginModel): Call<LoginResponse>

    @POST("Api/Employee/Attendace/Add")
    suspend fun addAttendance(@Body requestAddendance: AttendanceModel): AttendanceResponse
}