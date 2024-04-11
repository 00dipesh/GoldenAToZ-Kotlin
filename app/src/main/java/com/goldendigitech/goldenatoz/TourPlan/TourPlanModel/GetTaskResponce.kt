package com.goldendigitech.goldenatoz.TourPlan.TourPlanModel

import com.google.gson.annotations.SerializedName

data class GetTaskResponce(

    @SerializedName("Success") val success: Boolean,
    @SerializedName("Data") val data: Map<Int, String>,
    @SerializedName("Message") val message: String

)