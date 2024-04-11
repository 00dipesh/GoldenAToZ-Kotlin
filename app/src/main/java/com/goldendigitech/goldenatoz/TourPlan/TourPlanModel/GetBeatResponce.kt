package com.goldendigitech.goldenatoz.TourPlan.TourPlanModel

import com.google.gson.annotations.SerializedName

data class GetBeatResponce(
    @SerializedName("Success" ) var Success : Boolean?        = null,
    @SerializedName("Data"    ) var Data    : ArrayList<BeatData> = arrayListOf(),
    @SerializedName("Message" ) var Message : String?         = null
)
data class BeatData(
    @SerializedName("Name"        ) var Name        : String?  = null,
    @SerializedName("Description" ) var Description : String?  = null,
    @SerializedName("SSId"        ) var SSId        : Int?     = null,
    @SerializedName("Id"          ) var Id          : Int?     = null,
    @SerializedName("IsActive"    ) var IsActive    : Boolean? = null,
    @SerializedName("CreatedAt"   ) var CreatedAt   : String?  = null,
    @SerializedName("CreatedBy"   ) var CreatedBy   : Int?     = null,
    @SerializedName("UpdatedAt"   ) var UpdatedAt   : String?  = null,
    @SerializedName("UpdatedBy"   ) var UpdatedBy   : String?  = null

)