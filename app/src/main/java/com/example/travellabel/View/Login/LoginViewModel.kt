package com.example.travellabel.View.Login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travellabel.Data.api.LoginRequest
import com.example.travellabel.Data.pref.UserModel
import com.example.travellabel.Data.pref.UserRepository
import com.example.travellabel.Response.LoginResponse
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    val loginResult: MutableLiveData<LoginResponse> = repository.loginResult

    fun login(
        identifier: String,
        password: String,
        onResult: (LoginResponse?) -> Unit,
        onError: (String?) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val request = LoginRequest(identifier, password)
                val response = repository.login(request)
                onResult(response)
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, LoginResponse::class.java)
                onError(errorResponse.message)
            } catch (e: Exception) {
                onError(e.message)
            }
        }
    }

    suspend fun saveSession(user: UserModel){
        repository.saveSession(user)
    }
}