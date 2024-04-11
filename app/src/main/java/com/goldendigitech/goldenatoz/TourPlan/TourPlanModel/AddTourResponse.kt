package com.goldendigitech.goldenatoz.TourPlan.TourPlanModel

import com.google.gson.annotations.SerializedName

data class AddTourResponse (
    @SerializedName("Success" ) var Success : Boolean? = null,
    @SerializedName("Message" ) var Message : String?  = null
)