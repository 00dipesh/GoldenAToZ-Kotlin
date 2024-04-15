package com.goldendigitech.goldenatoz.Holiday

import com.google.gson.annotations.SerializedName

data class HolidayResponse(
    @SerializedName("Success") val success: Boolean,
    @SerializedName("Data") val data: List<Holiday>,
    @SerializedName("Message") val message: String
)

data class Holiday(
    @SerializedName("HDate") val hDate: String,
    @SerializedName("Name") val name: String,
    @SerializedName("Id") val id: Int,
    @SerializedName("IsActive") val isActive: Boolean,
    @SerializedName("CreatedAt") val createdAt: String,
    @SerializedName("CreatedBy") val createdBy: Int,
    @SerializedName("UpdatedAt") val updatedAt: String?, // Nullable type declaration
    @SerializedName("UpdatedBy") val updatedBy: String? // Nullable type declaration
)
