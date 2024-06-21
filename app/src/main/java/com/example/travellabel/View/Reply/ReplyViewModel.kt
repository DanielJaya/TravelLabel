package com.example.travellabel.View.Reply

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travellabel.Data.pref.UserRepository
import com.example.travellabel.Response.RepliesItem
import kotlinx.coroutines.launch

class ReplyViewModel(private val repository: UserRepository) : ViewModel() {
    private var _listReply = MutableLiveData<List<RepliesItem>>()
    val listReply: LiveData<List<RepliesItem>> = _listReply

    fun getReply(discussionId: String) {
        viewModelScope.launch {
            repository.getReply(discussionId).collect {
                it.onSuccess {
                    _listReply.postValue(it.replies)
                }
            }
        }
    }
}