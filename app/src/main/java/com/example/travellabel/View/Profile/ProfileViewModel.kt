package com.example.travellabel.View.Profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.travellabel.Data.pref.UserModel
import com.example.travellabel.Data.pref.UserRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: UserRepository) : ViewModel() {
    private var _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private var _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    fun getUser(username: String) {
        var getUserJob: Job = Job()
        viewModelScope.launch {
            repository.getUser(username).collect {
                it.onSuccess {
                    _username.postValue(it.user.username)
                    _email.postValue(it.user.email)
                }
            }
        }
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    suspend fun logout() {
        repository.logout()
    }
}
