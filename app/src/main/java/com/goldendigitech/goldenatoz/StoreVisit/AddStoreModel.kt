package com.goldendigitech.goldenatoz.StoreVisit

import com.google.gson.annotations.SerializedName

class AddStoreModel (

    @SerializedName("Name"          ) var Name          : String? = null,
    @SerializedName("TurnOver"      ) var TurnOver      : String? = null,
    @SerializedName("GstNo"         ) var GstNo         : String? = null,
    @SerializedName("ContactNumber" ) var ContactNumber : String? = null,
    @SerializedName("Address"       ) var Address       : String? = null,
    @SerializedName("State"         ) var State         : String? = null,
    @SerializedName("City"          ) var City          : String? = null,
    @SerializedName("Description"   ) var Description   : String? = null
)