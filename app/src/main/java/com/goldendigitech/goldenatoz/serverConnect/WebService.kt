package com.goldendigitech.goldenatoz.serverConnect

import com.goldendigitech.goldenatoz.login.LoginModel
import com.goldendigitech.goldenatoz.login.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface WebService {

//    @POST("Api/Account/Register")
//    fun registerOrganisation(@Body registerModel: RegisterModel): Call<RegisterResponce>

//    @GET("Api/Employee/Get")
//    fun getEmployee(@Query("employeeId") employeeId: Int?): Call<GetEmployeeModel>

    @POST("Api/Employee/Login")
    fun userLogin(@Body loginModel: LoginModel): Call<LoginResponse>


}