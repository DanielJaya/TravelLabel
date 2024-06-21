package com.example.travellabel.Response

import com.google.gson.annotations.SerializedName

data class AddReplyResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("reply")
	val reply: ReplyAddItem,

	@field:SerializedName("status")
	val status: String
)

data class ReplyAddItem(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("discussionId")
	val discussionId: String,

	@field:SerializedName("creatorId")
	val creatorId: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("content")
	val content: String
)
