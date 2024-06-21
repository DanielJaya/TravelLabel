package com.example.travellabel.Response

import com.google.gson.annotations.SerializedName


data class ReviewResponse(
	val status: String,
	val message: String,
	val allReviews: List<Review>
)

data class Review(
	val id: String,
	val locationId: String,
	val userId: String,
	val rating: String,
	val content: String,
	val createdAt: String,
	val updatedAt: String,
	val interactions: List<Any>,
	val totalLike: Int,
	val totalDislike: Int,
	val user: User1
)

data class User1(
	@field:SerializedName("userId")
	val userId: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)