package com.goldendigitech.goldenatoz.TourPlan.TourPlanModel

import com.google.gson.annotations.SerializedName

data class MonthlyTourModel(

    @SerializedName("Date"            ) var Date            : String?  = null,
    @SerializedName("Day"             ) var Day             : String?  = null,
    @SerializedName("TaskOfDay"       ) var TaskOfDay       : String?  = null,
    @SerializedName("State"           ) var State           : String?  = null,
    @SerializedName("BeatName"        ) var BeatName        : String?  = null,
    @SerializedName("DistributorName" ) var DistributorName : String?  = null,
    @SerializedName("SsName"          ) var SsName          : String?  = null,
    @SerializedName("Town"            ) var Town            : String?  = null,
    @SerializedName("Id"              ) var Id              : Int?     = null,
    @SerializedName("IsActive"        ) var IsActive        : Boolean? = null,
    @SerializedName("CreatedAt"       ) var CreatedAt       : String?  = null,
    @SerializedName("CreatedBy"       ) var CreatedBy       : Int?     = null,
    @SerializedName("UpdatedAt"       ) var UpdatedAt       : String?  = null,
    @SerializedName("UpdatedBy"       ) var UpdatedBy       : String?  = null

)