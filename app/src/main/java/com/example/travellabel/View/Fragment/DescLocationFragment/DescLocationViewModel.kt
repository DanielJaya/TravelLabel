package com.example.travellabel.View.Fragment.DescLocationFragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travellabel.Data.api.ApiService
import com.example.travellabel.Data.pref.UserRepository
import com.example.travellabel.Request.ReviewRequest
import com.example.travellabel.Response.BookmarkResponse
import com.example.travellabel.Response.Review
import com.example.travellabel.Response.User1
import kotlinx.coroutines.launch

class DescLocationViewModel(private val repository: UserRepository, private val apiService: ApiService) : ViewModel() {

    private val _bookmarkStatus = MutableLiveData<Boolean>()
    val bookmarkStatus: LiveData<Boolean> = _bookmarkStatus

    private val _bookmarkResponse = MutableLiveData<BookmarkResponse>()
    val bookmarkResponse: LiveData<BookmarkResponse> = _bookmarkResponse

    private val _reviews = MutableLiveData<List<Review>?>()
    val reviews: MutableLiveData<List<Review>?> get() = _reviews

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _reviewSubmissionStatus = MutableLiveData<Boolean>()
    val reviewSubmissionStatus: LiveData<Boolean> get() = _reviewSubmissionStatus


    fun fetchReviews(locationId: String) {
        Log.d("DescLocationViewModel", "Fetching reviews for location: $locationId")

        viewModelScope.launch {
            try {
                val response = repository.getReviews(locationId)
                Log.d("DescLocationViewModel", "API Response: $response")
                if (response.status == "OK") {
                    response.allReviews?.let {
                        _reviews.postValue(it)
                        Log.d("DescLocationViewModel", "Reviews fetched: ${it.size}")
                    } ?: run {
                        Log.e("DescLocationViewModel", "No reviews found")
                        _reviews.postValue(emptyList())
                    }
                } else {
                    Log.e("DescLocationViewModel", "Failed to fetch reviews: ${response.message}")
                }
            } catch (e: Exception) {
                Log.e("DescLocationViewModel", "Error fetching reviews", e)
            }
        }
    }



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

    fun submitReview(locationId: String, content: String, rating: String, token: String) {
        viewModelScope.launch {
            try {
                val request = ReviewRequest(locationId, rating, content)
                val response = repository.createReview(token, request)
                if (response.status == "OK") {
                    _reviewSubmissionStatus.postValue(true)
                    fetchReviews(locationId) // Refresh reviews after submission
                } else {
                    _reviewSubmissionStatus.postValue(false)
                }
            } catch (e: Exception) {
                _reviewSubmissionStatus.postValue(false)
            }
        }
    }
}