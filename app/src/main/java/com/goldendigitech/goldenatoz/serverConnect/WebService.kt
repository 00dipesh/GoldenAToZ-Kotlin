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

import com.goldendigitech.goldenatoz.Feedback.FeedbackModel
import com.goldendigitech.goldenatoz.Feedback.FeedbackResponse
import com.goldendigitech.goldenatoz.Holiday.HolidayResponse
import com.goldendigitech.goldenatoz.Leave.Response.AddLeaveModel
import com.goldendigitech.goldenatoz.Leave.Response.AddLeaveResponse
import com.goldendigitech.goldenatoz.Leave.Response.LeaveStatusResponse
import com.goldendigitech.goldenatoz.SalarySlip.SalarySlipResponse
import com.goldendigitech.goldenatoz.UpdatePersonalInfo.UpdatePersonalInfoModel
import com.goldendigitech.goldenatoz.UpdatePersonalInfo.UpdatePersonalInfoResponse
import com.goldendigitech.goldenatoz.UpdatePersonalInfo.UploadDocumentModel
import com.goldendigitech.goldenatoz.UpdatePersonalInfo.UploadDocumentResponse

import com.goldendigitech.goldenatoz.attendance.AttendanceModel
import com.goldendigitech.goldenatoz.attendance.AttendanceResponse
import com.goldendigitech.goldenatoz.attendance.TaskResponse
import com.goldendigitech.goldenatoz.attendance.WorkingWithResponse
import com.goldendigitech.goldenatoz.employee.DocumentResponse
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

    @POST("Api/Account/Feedback/Add")
    fun  addFeedback(@Body requestFeedback : FeedbackModel) : Call<FeedbackResponse>

    @GET("Api/Employee/Document/GetDocumentsByEmpId")
    fun showFile(@Query("Id") Id:Int): Call<DocumentResponse>

    @GET("Api/Employee/Document/GetSalarySlipByEmpId")
    fun showSalarySlipList(@Query("Id") Id:Int) : Call<SalarySlipResponse>

    @POST("Api/Employee/Update")
    fun updateUserData(@Body requestUpdatePersonalInfo : UpdatePersonalInfoModel): Call<UpdatePersonalInfoResponse>

    @POST("Api/Employee/Document/Add")
    fun uploadUserDocument(@Body requestDocument : UploadDocumentModel): Call<UploadDocumentResponse>

    @GET("Api/Account/HolidayList/GetAll")
    fun showHolidayList(): Call<HolidayResponse>

    @POST("Api/Employee/Leave/Add")
    fun addLeave(@Body addLeaveModel: AddLeaveModel): Call<AddLeaveResponse>

    @GET("Api/Employee/Leave/GetLeaveByEmpIdAndStatus")
    fun showLeaveStatus(@Query("EmpId") empId: Int, @Query("Status") status: String): Call<LeaveStatusResponse>



}