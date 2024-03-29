package com.goldendigitech.goldenatoz.attendance

import com.google.gson.annotations.SerializedName

data class TaskResponse(
    @SerializedName("Success") val success: Boolean,
    @SerializedName("Data") val data: Map<String, String>, // Map of ID to name
    @SerializedName("Message") val message: String?
)
