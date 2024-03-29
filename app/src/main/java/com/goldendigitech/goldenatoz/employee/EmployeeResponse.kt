package com.goldendigitech.goldenatoz.employee

import com.google.gson.annotations.SerializedName

data class EmployeeResponse(
    @SerializedName("Success") val success: Boolean,
    @SerializedName("Data") val data: Employee?,
    @SerializedName("Message") val message: String?
)
data class Employee(
    @SerializedName("PersonRoleStatus") val personRoleStatus: String?,
    @SerializedName("Users") val users: Any?,
    @SerializedName("Details") val details: Any?,
    @SerializedName("FirstName") val firstName: String,
    @SerializedName("MiddleName") val middleName: String?,
    @SerializedName("LastName") val lastName: String,
    @SerializedName("EmployeeCode") val employeeCode: String,
    @SerializedName("CompanyName") val companyName: String,
    @SerializedName("WorkTypeId") val workTypeId: Int,
    @SerializedName("ZoneId") val zoneId: Int,
    @SerializedName("RoleId") val roleId: Int,
    @SerializedName("ReportingToId") val reportingToId: Int,
    @SerializedName("CompanyId") val companyId: Int,
    @SerializedName("Username") val username: String,
    @SerializedName("Password") val password: String,
    @SerializedName("Contact") val contact: String,
    @SerializedName("AlternateContact") val alternateContact: String,
    @SerializedName("MaritalStatus") val maritalStatus: String,
    @SerializedName("Skill") val skill: String,
    @SerializedName("WorkExperience") val workExperience: Int,
    @SerializedName("Qualification") val qualification: String,
    @SerializedName("OfficialNumber") val officialNumber: Any?,
    @SerializedName("DOB") val dob: String,
    @SerializedName("Age") val age: Int,
    @SerializedName("Gender") val gender: String,
    @SerializedName("PermanentAddress") val permanentAddress: String,
    @SerializedName("Address") val address: String,
    @SerializedName("Email") val email: String,
    @SerializedName("CreatedDate") val createdDate: Any?,
    @SerializedName("JoiningDate") val joiningDate: String,
    @SerializedName("Designation") val designation: String,
    @SerializedName("BloodGroup") val bloodGroup: String,
    @SerializedName("Dept") val dept: String,
    @SerializedName("DeactivatedDate") val deactivatedDate: Any?,
    @SerializedName("LastSyncDate") val lastSyncDate: String,
    @SerializedName("LastAttendanceDate") val lastAttendanceDate: String,
    @SerializedName("AppVersion") val appVersion: String,
    @SerializedName("IMEI") val imei: String,
    @SerializedName("Status") val status: String,
    @SerializedName("BankName") val bankName: Any?,
    @SerializedName("BankAccountNumber") val bankAccountNumber: Any?,
    @SerializedName("CTC") val ctc: Int,
    @SerializedName("PAN") val pan: Any?,
    @SerializedName("Id") val id: Int,
    @SerializedName("IsActive") val isActive: Boolean,
    @SerializedName("CreatedAt") val createdAt: String,
    @SerializedName("CreatedBy") val createdBy: Int,
    @SerializedName("UpdatedAt") val updatedAt: String,
    @SerializedName("UpdatedBy") val updatedBy: Int
)





