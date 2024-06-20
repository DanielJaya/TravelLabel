package com.example.travellabel.View.CreateForum

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travellabel.Data.pref.UserRepository
import com.example.travellabel.Request.CreateDiscussionRequest
import com.example.travellabel.Response.CreateDiscussionItem
import kotlinx.coroutines.launch

class CreateForumViewModel(private val repository: UserRepository) : ViewModel() {
    private var _discussion = MutableLiveData<CreateDiscussionItem>()
    val discussion: LiveData<CreateDiscussionItem> = _discussion

    fun createDiscussion(createDiscussionRequest: CreateDiscussionRequest) {
        viewModelScope.launch {
            repository.createDiscussion(createDiscussionRequest).collect {
                it.onSuccess {
                    _discussion.postValue(it.discussion)
                }
            }
        }
    }
}