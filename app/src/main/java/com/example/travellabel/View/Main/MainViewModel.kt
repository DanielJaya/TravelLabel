package com.example.travellabel.View.Main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.example.travellabel.Data.pref.Output
import com.example.travellabel.Data.pref.UserModel
import com.example.travellabel.Data.pref.UserRepository
import com.example.travellabel.Response.LocationResponse

class MainViewModel(private val repository: UserRepository) : ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun getLocation(): LiveData<Output<LocationResponse>> = liveData {
        emit(Output.Loading)
        try {
            val response = repository.getLoc()
            val limitedLocations = response.locations.take(6)
            val limitedResponse = LocationResponse(limitedLocations)
            emit(Output.Success(limitedResponse))
        } catch (e: Exception) {
            emit(Output.Error(e.message ?: "Unknown Error"))
        }
    }
}