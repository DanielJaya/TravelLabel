package com.example.travellabel.Response

import com.google.gson.annotations.SerializedName

data class LocationResponse(

	@field:SerializedName("locations")
	val locations: List<LocationsItem>,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class LocationsItem(

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
