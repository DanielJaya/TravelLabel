package com.example.travellabel.View.Fragment.DescLocationFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travellabel.Data.pref.UserRepository
import com.example.travellabel.Response.BookmarkResponse
import kotlinx.coroutines.launch

class DescLocationViewModel(private val repository: UserRepository) : ViewModel() {

    private val _bookmarkStatus = MutableLiveData<Boolean>()
    val bookmarkStatus: LiveData<Boolean> = _bookmarkStatus

    private val _bookmarkResponse = MutableLiveData<BookmarkResponse>()
    val bookmarkResponse: LiveData<BookmarkResponse> = _bookmarkResponse

    fun addBookmark(locationId: String, token: String) {
        viewModelScope.launch {
            try {
                val response = repository.addBookmark(locationId, token)
                if (response.status == "OK") {
                    _bookmarkStatus.value = true
                    _bookmarkResponse.value = response
                } else {

                }
            } catch (e: Exception) {

            }
        }
    }

    fun removeBookmark(locationId: String, token: String) {
        viewModelScope.launch {
            try {
                val response = repository.removeBookmark(locationId, token)
                if (response.status == "OK") {
                    _bookmarkStatus.value = false
                    _bookmarkResponse.value = response
                } else {

                }
            } catch (e: Exception) {
            }
        }
    }
}