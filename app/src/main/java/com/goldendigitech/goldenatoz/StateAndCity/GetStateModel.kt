package com.goldendigitech.goldenatoz.StateAndCity

import com.google.gson.annotations.SerializedName


data class GetStateModel (
    @SerializedName("Success" ) var Success : Boolean?        = null,
    @SerializedName("Data"    ) var Data    : ArrayList<StateData> = arrayListOf(),
    @SerializedName("Message" ) var Message : String?         = null
)
data class StateData(
    @SerializedName("StateName"   ) var StateName   : String?  = null,
    @SerializedName("Description" ) var Description : String?  = null,
    @SerializedName("ZoneId"      ) var ZoneId      : String?  = null,
    @SerializedName("Id"          ) var Id          : Int?     = null,
    @SerializedName("IsActive"    ) var IsActive    : Boolean? = null,
    @SerializedName("CreatedAt"   ) var CreatedAt   : String?  = null,
    @SerializedName("CreatedBy"   ) var CreatedBy   : Int?     = null,
    @SerializedName("UpdatedAt"   ) var UpdatedAt   : String?  = null,
    @SerializedName("UpdatedBy"   ) var UpdatedBy   : String?  = null

)
