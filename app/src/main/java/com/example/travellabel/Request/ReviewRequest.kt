package com.example.travellabel.Request

data class ReviewRequest(
    val locationId: String,
    val rating: String,
    val content: String
)