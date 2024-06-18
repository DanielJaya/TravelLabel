package com.example.travellabel.Data.pref

data class UserModel (
    val username: String,
    val status: String,
    val message: String,
    val accessToken: String,
    val refreshToken: String,
    val isLogin: Boolean = false
)