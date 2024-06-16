package com.example.travellabel.Data.pref

data class UserModel (
    val token: String,
    val name: String,
    val userId: String,
    val status: String,
    val isLogin: Boolean = false
)