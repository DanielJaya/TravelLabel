package com.example.travellabel.Request

data class CreateLocationRequest (
    val label : String,
    val description : String,
    val lat : Double,
    val lon : Double
)