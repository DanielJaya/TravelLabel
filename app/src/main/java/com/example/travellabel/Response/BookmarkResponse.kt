package com.example.travellabel.Response

import com.google.gson.annotations.SerializedName

data class BookmarkResponse(
	@SerializedName("bookmarkedLocations")
	val locations: List<LocationsItem1?>? = null,

	@SerializedName("message")
	val message: String? = null,

	@SerializedName("status")
	val status: String? = null
)

data class LocationsItem1(

	@field:SerializedName("raters")
	val raters: String? = null,

	@field:SerializedName("rating")
	val rating: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("lon")
	val lon: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("label")
	val label: String? = null,

	@field:SerializedName("lat")
	val lat: String? = null
)
