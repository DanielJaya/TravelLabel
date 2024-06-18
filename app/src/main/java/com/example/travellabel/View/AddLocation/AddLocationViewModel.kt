package com.example.travellabel.View.AddLocation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travellabel.Request.CreateLocationRequest
import com.example.travellabel.Data.pref.Output
import com.example.travellabel.Data.pref.UserRepository
import com.example.travellabel.Response.CreateLocationResponse
import kotlinx.coroutines.launch

class AddLocationViewModel (private val repository: UserRepository) : ViewModel() {

    private val _createLocationResult = MutableLiveData<Output<CreateLocationResponse>>()
    val createLocationResult: LiveData<Output<CreateLocationResponse>> = _createLocationResult

    fun createLocation(request: CreateLocationRequest) {
        viewModelScope.launch {
            _createLocationResult.value = Output.Loading
            try {
                val response = repository.createLocation(request)
                if (response.status == "OK") {
                    _createLocationResult.value = Output.Success(response)
                } else {
                    _createLocationResult.value = Output.Error(response.message.toString())
                }
            } catch (e: Exception) {
                _createLocationResult.value = Output.Error(e.message.toString())
            }
        }
    }
}