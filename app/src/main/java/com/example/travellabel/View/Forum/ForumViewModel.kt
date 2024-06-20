package com.example.travellabel.View.Forum

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.travellabel.Data.pref.UserModel
import com.example.travellabel.Data.pref.UserRepository
import com.example.travellabel.Response.DiscussionsItem
import kotlinx.coroutines.launch

class ForumViewModel(private val repository: UserRepository) : ViewModel() {
    private var _listDiscussion = MutableLiveData<List<DiscussionsItem>>()
    val listDiscussion: LiveData<List<DiscussionsItem>> = _listDiscussion

    fun getDiscussion(locationId: String) {
        viewModelScope.launch {
            repository.getDiscussion(locationId).collect {
                it.onSuccess {
                    _listDiscussion.postValue(it.discussions)
                }
            }
        }
    }
}