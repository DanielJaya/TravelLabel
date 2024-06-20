package com.example.travellabel.Response

import com.google.gson.annotations.SerializedName

data class GetDiscussionResponse(

    @field:SerializedName("discussions")
    val discussions: List<DiscussionsItem>,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: String
)

data class DiscussionsItem(

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("locationId")
    val locationId: String,

    @field:SerializedName("creatorId")
    val creatorId: String,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("content")
    val content: String
)
