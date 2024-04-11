package com.goldendigitech.goldenatoz.TourPlan.TourPlanModel

import com.google.gson.annotations.SerializedName

data class GetSSResponce(
    @SerializedName("Success" ) var Success : Boolean?        = null,
    @SerializedName("Data"    ) var Data    : ArrayList<SSData> = arrayListOf(),
    @SerializedName("Message" ) var Message : String?         = null
)
data class SSData (

    @SerializedName("Name"        ) var Name        : String?  = null,
    @SerializedName("Description" ) var Description : String?  = null,
    @SerializedName("TownId"      ) var TownId      : Int?     = null,
    @SerializedName("Id"          ) var Id          : Int?     = null,
    @SerializedName("IsActive"    ) var IsActive    : Boolean? = null,
    @SerializedName("CreatedAt"   ) var CreatedAt   : String?  = null,
    @SerializedName("CreatedBy"   ) var CreatedBy   : Int?     = null,
    @SerializedName("UpdatedAt"   ) var UpdatedAt   : String?  = null,
    @SerializedName("UpdatedBy"   ) var UpdatedBy   : String?  = null

)