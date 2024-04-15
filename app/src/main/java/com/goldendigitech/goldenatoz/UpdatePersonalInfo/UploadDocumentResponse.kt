package com.goldendigitech.goldenatoz.UpdatePersonalInfo

import com.google.gson.annotations.SerializedName

data class UploadDocumentResponse( @SerializedName("Success" ) var Success : Boolean? = null,
                                   @SerializedName("Message" ) var Message : String?  = null)
