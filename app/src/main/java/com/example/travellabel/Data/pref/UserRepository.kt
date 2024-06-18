package com.example.travellabel.Data.pref

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.travellabel.Data.api.ApiService
import com.example.travellabel.Request.CreateLocationRequest
import com.example.travellabel.Request.LoginRequest
import com.example.travellabel.Request.RegisterRequest
import com.example.travellabel.Response.CreateLocationResponse
import com.example.travellabel.Response.GetUserResponse
import com.example.travellabel.Response.LocationResponse
import com.example.travellabel.Response.LoginResponse
import com.example.travellabel.Response.RegisterResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import retrofit2.Call

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
        return userPreference.getSession().map { user ->
            Log.d("UserRepository", "getSession: $user")
            user
        }
    }


    fun login(request: LoginRequest): Call<LoginResponse> {
        return apiService.login(request)
    }

    suspend fun logout() {
        userPreference.logout()
    }

    suspend fun getLocation() : LiveData<Output<LocationResponse>> = liveData {
        emit(Output.Loading)
        try {
            val response = apiService.getLocation()
            if (response.status == "OK") {
                emit(Output.Success(response))
            } else {
                emit(Output.Error(response.message.toString()))
            }
        } catch (e: Exception){
            emit(Output.Error(e.message.toString()))
        }
    }

    suspend fun getUser(username: String): Flow<Result<GetUserResponse>> {
        val getFlow = flow {
            try {
                val getUserResponse = apiService.getUser(username)
                emit(Result.success(getUserResponse))
            } catch (error: Exception) {
                error.printStackTrace()
                emit(Result.failure(error))
            }
        }.flowOn(Dispatchers.IO)
        return getFlow
    }

    suspend fun createLocation(request: CreateLocationRequest): CreateLocationResponse {
        return apiService.createLocation(request)
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