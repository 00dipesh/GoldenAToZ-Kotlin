package com.goldendigitech.goldenatoz.TourPlan.TourPlanModel

import androidx.lifecycle.LiveData

data class GetAllTourResponce (

    val Success:Boolean,
    var Data: List<MonthlyTourModel>? = null,
    val Message:String

)