package com.example.travellabel.Response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("“accessToken”")
	val accessToken: String,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("“refreshToken”")
	val refreshToken: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
