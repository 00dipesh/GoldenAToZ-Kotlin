package com.goldendigitech.goldenatoz.serverConnect

import com.goldendigitech.goldenatoz.ChangePassword.ChangepasswordModel
import com.goldendigitech.goldenatoz.ChangePassword.ChangepasswordResponse
import com.goldendigitech.goldenatoz.Complaint.ComplaintModel
import com.goldendigitech.goldenatoz.Complaint.ComplaintResponse
import com.goldendigitech.goldenatoz.StateAndCity.GetCityModel
import com.goldendigitech.goldenatoz.StateAndCity.GetStateModel
import com.goldendigitech.goldenatoz.TourPlan.TourPlanModel.AddTourModel
import com.goldendigitech.goldenatoz.TourPlan.TourPlanModel.AddTourResponse
import com.goldendigitech.goldenatoz.TourPlan.TourPlanModel.GetAllTourResponce
import com.goldendigitech.goldenatoz.TourPlan.TourPlanModel.GetBeatResponce
import com.goldendigitech.goldenatoz.TourPlan.TourPlanModel.GetDistributorResponce
import com.goldendigitech.goldenatoz.TourPlan.TourPlanModel.GetSSResponce
import com.goldendigitech.goldenatoz.TourPlan.TourPlanModel.GetTaskResponce
import com.goldendigitech.goldenatoz.TourPlan.TourPlanModel.GetTownResponce
import com.goldendigitech.goldenatoz.attendance.AttendanceModel
import com.goldendigitech.goldenatoz.attendance.AttendanceResponse
import com.goldendigitech.goldenatoz.attendance.TaskResponse
import com.goldendigitech.goldenatoz.attendance.WorkingWithResponse
import com.goldendigitech.goldenatoz.employee.EmployeeResponse
import com.goldendigitech.goldenatoz.login.LoginModel
import com.goldendigitech.goldenatoz.login.LoginResponse
import com.goldendigitech.goldenatoz.login.LogoutResponce
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface WebService {

    @POST("Api/Employee/Login")   // Login
    fun userLogin(@Body loginModel: LoginModel): Call<LoginResponse>

    @GET("Api/Employee/Get")  // Employee Data Get
    fun getEmployee(@Query("employeeId") employeeId: Int): Call<EmployeeResponse>

    @GET("Api/Employee/GetIdAndName") //Senior & junior List
    fun getWorkingWithModel(): Call<WorkingWithResponse>

    @POST("Api/Employee/Attendace/Add")  // Add Attendance
    fun addAttendance(@Body requestAddendance: AttendanceModel): Call<AttendanceResponse>

    @GET("Api/Account/DailyTask/GetTaskIdAndNameDict")   //Task List
    fun getTaskList(): Call<TaskResponse>

    @POST("Api/Account/Complaint/Add")    //add complaint
    fun addComplaint(@Body requestComplaint: ComplaintModel): Call<ComplaintResponse>

    @POST("Api/Employee/ChangePassword")
    fun changePasswords(@Body requestChangepasswordModel: ChangepasswordModel): Call<ChangepasswordResponse>

    @POST("Api/Employee/Logout")
    fun logoutUser(
        @Query("Email") Email: String,
        @Query("Contact") Contact: String
    ): Call<LogoutResponce>

    @GET("Api/SupplyChain/TourPlan/GetAll")
    fun getAllTourPlanList(): Call<GetAllTourResponce>

    @GET("Api/Account/State/GetAll")
    fun GetState(): Call<GetStateModel>

    @GET("Api/Account/City/GetIdAndNameByStateId")
    fun getCitiesByStateId(@Query("Id") stateId: Int): Call<GetCityModel>

    @GET("Api/Account/Town/GetAll")
    fun TOWN_MODEL_CALL() :Call<GetTownResponce>

    @GET("Api/Account/DailyTask/GetTaskIdAndNameDict")
    fun GET_TASK_MODEL_CALL(): Call<GetTaskResponce>

    @GET("Api/Account/SaleStage/GetAll")
    fun SS_MODEL_CALL() :Call<GetSSResponce>

    @GET("Api/SupplyChain/Destributor/GetAll")
    fun getDistributorList() :Call<GetDistributorResponce>

    @GET("Api/Account/Beat/GetAll")
    fun BEAT_MODEL_CALL() :Call<GetBeatResponce>

    @POST("Api/SupplyChain/TourPlan/Add")
    fun addTourPlan(@Body addTourModel: AddTourModel) :Call<AddTourResponse>



}