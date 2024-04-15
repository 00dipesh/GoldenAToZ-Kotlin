package com.goldendigitech.goldenatoz.StateAndCity

data class GetCityModel (

    var Success:Boolean,
    var Data: Map<String, String> = emptyMap(),
    var Message:String
)