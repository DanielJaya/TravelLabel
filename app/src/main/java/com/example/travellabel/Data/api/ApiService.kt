package com.example.travellabel.Data.api

import com.example.travellabel.Response.LocationResponse
import com.example.travellabel.Response.LoginResponse
import com.example.travellabel.Response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest): RegisterResponse

    @POST("auth/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @GET("location")
    suspend fun getLocation(
        @Query("location") location: Int = 1
    ) : LocationResponse
}