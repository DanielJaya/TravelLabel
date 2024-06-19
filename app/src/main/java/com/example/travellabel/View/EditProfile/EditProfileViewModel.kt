package com.example.travellabel.View.EditProfile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.travellabel.Data.pref.UserModel
import com.example.travellabel.Data.pref.UserRepository
import com.example.travellabel.Request.UpdateProfileRequest
import kotlinx.coroutines.launch

class EditProfileViewModel(private val repository: UserRepository) : ViewModel() {
    private var _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private var _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    fun updateUser(username: String, updateProfileRequest: UpdateProfileRequest) {
        viewModelScope.launch {
            repository.updateUser(username, updateProfileRequest).collect {
                it.onSuccess {
                    _username.postValue(it.user.username)
                    _email.postValue(it.user.email)
                }
            }
        }
    }

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}