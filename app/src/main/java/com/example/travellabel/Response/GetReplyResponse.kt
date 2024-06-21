package com.example.travellabel.Response

import com.google.gson.annotations.SerializedName

data class GetReplyResponse(

	@field:SerializedName("replies")
	val replies: List<RepliesItem>,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class RepliesItem(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("creator")
	val creator: CreatorReply,

	@field:SerializedName("discussionId")
	val discussionId: String,

	@field:SerializedName("creatorId")
	val creatorId: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("content")
	val content: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)

data class CreatorReply(

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("username")
	val username: String
)
