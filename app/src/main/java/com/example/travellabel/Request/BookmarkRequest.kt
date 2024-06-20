package com.example.travellabel.Request

import com.google.gson.annotations.SerializedName

data class BookmarkRequest(
    @SerializedName("locationId") val locationId: String
)