package com.example.travellabel.Response

import com.google.gson.annotations.SerializedName

data class GetUserResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("user")
	val user: GetUser,

	@field:SerializedName("status")
	val status: String
)

data class GetUser(

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("username")
	val username: String,

	@field:SerializedName("name")
	val name: String
)
