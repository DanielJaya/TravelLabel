package com.example.travellabel.View.Map

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.travellabel.Data.pref.UserRepository
import com.example.travellabel.Response.LocationResponse

class MapsViewModel (private val repository: UserRepository): ViewModel() {
    suspend fun getLocation(): LiveData<com.example.travellabel.Data.pref.Output<LocationResponse>> {
        return repository.getLocation()

    }
}