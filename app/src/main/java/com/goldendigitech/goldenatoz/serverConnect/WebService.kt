package com.goldendigitech.goldenatoz.serverConnect

import com.goldendigitech.goldenatoz.ChangePassword.ChangepasswordModel
import com.goldendigitech.goldenatoz.ChangePassword.ChangepasswordResponse
import com.goldendigitech.goldenatoz.Complaint.ComplaintModel
import com.goldendigitech.goldenatoz.Complaint.ComplaintResponse
import com.goldendigitech.goldenatoz.Feedback.FeedbackModel
import com.goldendigitech.goldenatoz.Feedback.FeedbackResponse
import com.goldendigitech.goldenatoz.SalarySlip.SalarySlipResponse
import com.goldendigitech.goldenatoz.UpdatePersonalInfo.UpdatePersonalInfoModel
import com.goldendigitech.goldenatoz.UpdatePersonalInfo.UpdatePersonalInfoResponse
import com.goldendigitech.goldenatoz.attendance.AttendanceModel
import com.goldendigitech.goldenatoz.attendance.AttendanceResponse
import com.goldendigitech.goldenatoz.attendance.TaskResponse
import com.goldendigitech.goldenatoz.attendance.WorkingWithResponse
import com.goldendigitech.goldenatoz.employee.DocumentResponse
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

    @POST("Api/Account/Complaint/Add")    //add complaint
    fun addComplaint(@Body requestComplaint : ComplaintModel) : Call<ComplaintResponse>

    @POST("Api/Employee/ChangePassword")
    fun changePasswords(@Body requestChangepasswordModel: ChangepasswordModel) : Call<ChangepasswordResponse>

    @POST("Api/Account/Feedback/Add")
    fun  addFeedback(@Body requestFeedback : FeedbackModel) : Call<FeedbackResponse>

    @GET("Api/Employee/Document/GetDocumentsByEmpId")
    fun showFile(@Query("Id") Id:Int): Call<DocumentResponse>

    @GET("Api/Employee/Document/GetSalarySlipByEmpId")
    fun showSalarySlipList(@Query("Id") Id:Int) : Call<SalarySlipResponse>

    @POST("Api/Employee/Update")
    fun updateUserData(@Body requestUpdatePersonalInfo : UpdatePersonalInfoModel): Call<UpdatePersonalInfoResponse>

}