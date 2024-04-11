package com.goldendigitech.goldenatoz.employee

import com.google.gson.annotations.SerializedName

data class DocumentResponse(
    @SerializedName("Success") var Success : Boolean,
    @SerializedName("Data") var Data : List<Document>,
    @SerializedName("Message") var Message : String
)

data class Document(
    @SerializedName("EmployeeId") val employeeId: Int,
    @SerializedName("FileName") val fileName: String,
    @SerializedName("FileType") val fileType: String,
    @SerializedName("FileContent") val fileContent: String,
    @SerializedName("Id") val id: Int,
    @SerializedName("IsActive") val isActive: Boolean,
    @SerializedName("CreatedAt") val createdAt: String,
    @SerializedName("CreatedBy") val createdBy: Int,
    @SerializedName("UpdatedAt") val updatedAt: String?, // Can be nullable
    @SerializedName("UpdatedBy") val updatedBy: Int? // Can be nullable
)