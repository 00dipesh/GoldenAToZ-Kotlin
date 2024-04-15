package com.goldendigitech.goldenatoz.Leave.Response

import com.google.gson.annotations.SerializedName

data class AddLeaveResponse (@SerializedName("Success") var Success : Boolean,
                             @SerializedName("Message") var Message : String)