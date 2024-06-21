package com.example.travellabel.View.AddReply

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travellabel.Data.pref.UserRepository
import com.example.travellabel.Request.AddReplyRequest
import com.example.travellabel.Response.ReplyAddItem
import kotlinx.coroutines.launch

class AddReplyViewModel(private val repository: UserRepository) : ViewModel() {
    private var _listDiscussion = MutableLiveData<ReplyAddItem>()
    val reply: LiveData<ReplyAddItem> = _listDiscussion

    fun addReply(discussionId: String, content: AddReplyRequest) {
        viewModelScope.launch {
            repository.addReply(discussionId, content).collect {
                it.onSuccess {
                    _listDiscussion.postValue(it.reply)
                }
            }
        }
    }
}