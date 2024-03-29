package com.goldendigitech.goldenatoz.attendance

import com.google.gson.annotations.SerializedName

data class WorkingWithResponse (

    @SerializedName("Success") val success: Boolean,
    @SerializedName("Data") val data: Map<Int, String>, // Map of ID to name
    @SerializedName("Message") val message: String?
)