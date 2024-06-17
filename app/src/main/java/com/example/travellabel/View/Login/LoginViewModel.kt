package com.example.travellabel.View.Login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.travellabel.Data.api.LoginRequest
import com.example.travellabel.Data.pref.UserModel
import com.example.travellabel.Data.pref.UserRepository
import com.example.travellabel.Response.LoginResponse
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.HttpException

class LoginViewModel(private val repository: UserRepository) : ViewModel() {
/*


private val _loginResult = MutableLiveData<LoginResponse>()
val loginResult: LiveData<LoginResponse> = _loginResult

fun login(identifier: String, password: String) {
    viewModelScope.launch {
        val request = LoginRequest(identifier, password)
        val response = repository.login(request)
        _loginResult.postValue(response)

        if (response.status == "OK") {
            val userModel = UserModel(
                token = response.accessToken,
                refreshToken = response.refreshToken ?: "",
                name = "User Name", // Anda bisa mendapatkan nama user dari response jika tersedia
                userId = "User ID", // Anda bisa mendapatkan ID user dari response jika tersedia
                status = response.status,
                isLogin = true
            )
            repository.saveSession(userModel)
        }
    }
}

suspend fun saveSession(user: UserModel){
    Log.d("LoginViewModel", "Saving session for user: ${user.name}")
    repository.saveSession(user)
}
}

private fun <T> MutableLiveData<T>.postValue(response: Call<T>) {
TODO("Not yet implemented")

 */
}


