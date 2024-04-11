package com.goldendigitech.goldenatoz.TourPlan.TourPlanModel

import androidx.lifecycle.LiveData

data class GetAllTourResponce (

    val Success:Boolean,
    var data: List<MonthlyTourModel>? = null,
    val Message:String

)