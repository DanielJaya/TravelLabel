package com.example.travellabel.Data.pref

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.travellabel.Data.api.ApiService
import com.example.travellabel.Data.api.LoginRequest
import com.example.travellabel.Data.api.RegisterRequest
import com.example.travellabel.Response.LoginResponse
import com.example.travellabel.Response.RegisterResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {
    suspend fun register(request: RegisterRequest) : RegisterResponse{
        return apiService.register(request)
    }

    private var _loginResult = MutableLiveData<LoginResponse>()
    var loginResult: MutableLiveData<LoginResponse> = _loginResult

    suspend fun saveSession(user : UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun login(request: LoginRequest): LoginResponse {
        return apiService.login(request)
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun clearInstance() {
            instance = null
        }

        fun getInstance(apiService: ApiService,userPreference: UserPreference  ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, userPreference)
            }.also { instance = it }
    }
}