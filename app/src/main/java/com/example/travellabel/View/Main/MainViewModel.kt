package com.example.travellabel.View.Main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.travellabel.Data.pref.UserModel
import com.example.travellabel.Data.pref.UserRepository

class MainViewModel(private val repository: UserRepository) : ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}