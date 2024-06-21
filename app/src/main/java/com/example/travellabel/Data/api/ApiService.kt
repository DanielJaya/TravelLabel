package com.example.travellabel.Data.api

import com.example.travellabel.Request.BookmarkRequest
import com.example.travellabel.Request.CreateDiscussionRequest
import com.example.travellabel.Request.CreateLocationRequest
import com.example.travellabel.Request.LoginRequest
import com.example.travellabel.Request.RegisterRequest
import com.example.travellabel.Request.ReviewRequest
import com.example.travellabel.Request.UpdateProfileRequest
import com.example.travellabel.Response.CreateLocationResponse
import com.example.travellabel.Response.GetUserResponse
import com.example.travellabel.Response.LocationResponse
import com.example.travellabel.Response.LoginResponse
import com.example.travellabel.Response.RegisterResponse
import com.example.travellabel.Response.BookmarkResponse
import com.example.travellabel.Response.CreateDiscussionResponse
import com.example.travellabel.Response.GetDiscussionResponse
import com.example.travellabel.Response.ReviewResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("auth/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): RegisterResponse

    @POST("auth/login")
    fun login(
        @Body loginRequest: LoginRequest
    ): Call<LoginResponse>

    @GET("location")
    suspend fun getLocation(
        @Query("location") location: Int = 1
    ): LocationResponse

    @GET("profile/{username}")
    suspend fun getUser(
        @Path("username") username: String
    ): GetUserResponse

    @PUT("profile/{username}")
    suspend fun updateUser(
        @Path("username") username: String,
        @Body updateProfileRequest: UpdateProfileRequest
    ): GetUserResponse

    @POST("location")
    suspend fun createLocation(
        @Body addLocationRequest: CreateLocationRequest
    ): CreateLocationResponse

    @GET("bookmark")
    fun getBookmark(): Call<BookmarkResponse>

    @POST("bookmark")
    suspend fun addBookmark(
        @Header("Authorization") token: String,
        @Body request: BookmarkRequest
    ): BookmarkResponse

    @DELETE("bookmark")
    suspend fun removeBookmark(
        @Header("Authorization") token: String,
        @Body request: BookmarkRequest
    ): BookmarkResponse

    @GET("location/{locationId}/discussion")
    suspend fun getDiscussion(
        @Path("locationId") locationId: String,
    ): GetDiscussionResponse

    @POST("discussion")
    suspend fun createDiscussion(
        @Body createDiscussionRequest: CreateDiscussionRequest
    ): CreateDiscussionResponse

    @GET("location/{locationId}/review")
    suspend fun getReviewsByLocationId(
        @Path("locationId") locationId: String
        ): ReviewResponse

    @POST("review")
    suspend fun createReview(
        @Header("Authorization") token: String,
        @Body reviewRequest: ReviewRequest
    ): ReviewResponse
}