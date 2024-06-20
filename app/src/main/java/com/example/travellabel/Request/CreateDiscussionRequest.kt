package com.example.travellabel.Request

data class CreateDiscussionRequest(
    val locationId: String,
    val title: String,
    val content: String
)