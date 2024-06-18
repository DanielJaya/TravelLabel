package com.example.travellabel.View.Signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travellabel.Request.RegisterRequest
import com.example.travellabel.Data.pref.UserRepository
import com.example.travellabel.Response.RegisterResponse
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class SignupViewModel(private val repository: UserRepository) : ViewModel() {

    fun register(
        email: String,
        username: String,
        password: String,
        onResult: (RegisterResponse?) -> Unit,
        onError: (String?) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val request = RegisterRequest(email, username, password)
                val response = repository.register(request)
                onResult(response)
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, RegisterResponse::class.java)
                onError(errorResponse.message)
            } catch (e: Exception) {
                onError(e.message)
            }
        }
    }

}