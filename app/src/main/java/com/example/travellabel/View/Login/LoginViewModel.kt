package com.example.travellabel.View.Login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travellabel.Request.LoginRequest
import com.example.travellabel.Data.pref.UserModel
import com.example.travellabel.Data.pref.UserRepository
import com.example.travellabel.Response.LoginResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val repository: UserRepository) : ViewModel() {

    private val _loginResult = MutableLiveData<LoginResponse>()
    val loginResult: LiveData<LoginResponse> = _loginResult

    fun login(request: LoginRequest) {
        viewModelScope.launch {
            try {
                repository.login(request).enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        if (response.isSuccessful) {
                            _loginResult.value = response.body()
                        } else {
                            _loginResult.value = LoginResponse("", response.message(), "", response.code().toString())
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        _loginResult.value = LoginResponse("", t.message ?: "Unknown error", "", "500")
                    }
                })
            } catch (e: Exception) {
                _loginResult.value = LoginResponse("", e.message ?: "Unknown error", "", "500")
            }
        }
    }

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }
}
