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

data class CreatorDiscussion(

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("username")
    val username: String
)

data class DiscussionsItem(

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("creator")
    val creator: CreatorDiscussion,

    @field:SerializedName("locationId")
    val locationId: String,

    @field:SerializedName("creatorId")
    val creatorId: String,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("content")
    val content: String,

    @field:SerializedName("updatedAt")
    val updatedAt: String
)
