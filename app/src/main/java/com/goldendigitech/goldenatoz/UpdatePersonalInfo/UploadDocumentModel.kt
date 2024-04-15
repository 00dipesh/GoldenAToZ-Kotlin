package com.goldendigitech.goldenatoz.UpdatePersonalInfo

import com.google.gson.annotations.SerializedName

data class UploadDocumentModel (@SerializedName("EmployeeId"  ) var EmployeeId  : Int?    = null,
                                @SerializedName("FileName"    ) var FileName    : String? = null,
                                @SerializedName("FileType"    ) var FileType    : String? = null,
                                @SerializedName("FileContent" ) var FileContent : String? = null
)