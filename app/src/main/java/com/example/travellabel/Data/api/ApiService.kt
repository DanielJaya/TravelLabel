package com.example.travellabel.Data.api

import com.example.travellabel.Response.LoginResponse
import com.example.travellabel.Response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @POST("register")
    suspend fun register(@Body registerRequest: RegisterRequest): RegisterResponse

    @POST("login")
    fun login(@Body loginRequest: LoginRequest): LoginResponse
}