package com.goldendigitech.goldenatoz.TourPlan.TourPlanModel

import com.google.gson.annotations.SerializedName

data class AddTourModel (

    @SerializedName("Date"            ) var Date            : String? = null,
    @SerializedName("Day"             ) var Day             : String? = null,
    @SerializedName("TaskOfDay"       ) var TaskOfDay       : String? = null,
    @SerializedName("State"           ) var State           : String? = null,
    @SerializedName("BeatName"        ) var BeatName        : String? = null,
    @SerializedName("DistributorName" ) var DistributorName : String? = null,
    @SerializedName("SsName"          ) var SsName          : String? = null,
    @SerializedName("Town"            ) var Town            : String? = null
)