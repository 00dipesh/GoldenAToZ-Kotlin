package com.goldendigitech.goldenatoz.UpdatePersonalInfo

import com.google.gson.annotations.SerializedName

data class UpdatePersonalInfoModel(@SerializedName("FirstName"        ) var FirstName        : String? = null,
                                   @SerializedName("MiddleName"       ) var MiddleName       : String? = null,
                                   @SerializedName("LastName"         ) var LastName         : String? = null,
                                   @SerializedName("Contact"          ) var Contact          : String? = null,
                                   @SerializedName("AlternateContact" ) var AlternateContact : String? = null,
                                   @SerializedName("DOB"              ) var DOB              : String? = null,
                                   @SerializedName("Age"              ) var Age              : Int?    = null,
                                   @SerializedName("Gender"           ) var Gender           : String? = null,
                                   @SerializedName("BloodGroup"       ) var BloodGroup       : String? = null,
                                   @SerializedName("Address"          ) var Address          : String? = null,
                                   @SerializedName("PermanentAddress" ) var PermanentAddress : String? = null,
                                   @SerializedName("MaritalStatus"    ) var MaritalStatus    : String? = null,
                                   @SerializedName("Qualification"    ) var Qualification    : String? = null,
                                   @SerializedName("Skill"            ) var Skill            : String? = null,
                                   @SerializedName("WorkExperience"   ) var WorkExperience   : Int?    = null,
                                   @SerializedName("JoiningDate"      ) var JoiningDate      : String? = null,
                                   @SerializedName("Dept"             ) var Dept             : String? = null,
                                   @SerializedName("Designation"      ) var Designation      : String? = null,
                                   @SerializedName("Id"               ) var Id               : Int?    = null)


//data class UpdatePersonalInfoResponse(@SerializedName("Success" ) var Success : Boolean? = null,
//                                      @SerializedName("Message" ) var Message : String?  = null)

data class UpdatePersonalInfoResponse( val success: Boolean,
                                   val message: String)
