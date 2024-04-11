package com.goldendigitech.goldenatoz.TourPlan.TourPlanModel

import com.google.gson.annotations.SerializedName

data class GetDistributorResponce (

    @SerializedName("Success" ) var Success : Boolean?        = null,
    @SerializedName("Data"    ) var Data    : ArrayList<DistributorData> = arrayListOf(),
    @SerializedName("Message" ) var Message : String?         = null
)
data class DistributorData (

    @SerializedName("Name"             ) var Name             : String?  = null,
    @SerializedName("Address"          ) var Address          : String?  = null,
    @SerializedName("ContactNumber"    ) var ContactNumber    : String?  = null,
    @SerializedName("TelephoneNumber"  ) var TelephoneNumber  : String?  = null,
    @SerializedName("StockistId"       ) var StockistId       : Int?     = null,
    @SerializedName("ContactOwnerName" ) var ContactOwnerName : String?  = null,
    @SerializedName("PrimaryEmail"     ) var PrimaryEmail     : String?  = null,
    @SerializedName("SecondaryEmail"   ) var SecondaryEmail   : String?  = null,
    @SerializedName("Id"               ) var Id               : Int?     = null,
    @SerializedName("IsActive"         ) var IsActive         : Boolean? = null,
    @SerializedName("CreatedAt"        ) var CreatedAt        : String?  = null,
    @SerializedName("CreatedBy"        ) var CreatedBy        : String?  = null,
    @SerializedName("UpdatedAt"        ) var UpdatedAt        : String?  = null,
    @SerializedName("UpdatedBy"        ) var UpdatedBy        : String?  = null
)