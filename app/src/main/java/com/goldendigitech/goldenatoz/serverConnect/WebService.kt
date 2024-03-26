package com.goldendigitech.goldenatoz.serverConnect

import com.goldendigitech.goldenatoz.attendance.AttendanceModel
import com.goldendigitech.goldenatoz.attendance.AttendanceResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface WebService {
    @POST("Api/Employee/Attendace/Add")
    suspend fun addAttendance(@Body requestAddendance: AttendanceModel): AttendanceResponse
}