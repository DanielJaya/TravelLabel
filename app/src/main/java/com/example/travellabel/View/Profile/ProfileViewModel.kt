package com.example.travellabel.View.Profile

import androidx.lifecycle.ViewModel
import com.example.travellabel.Data.pref.UserRepository

class ProfileViewModel(private val repository: UserRepository) : ViewModel() {
    suspend fun logout() {
        repository.logout()
    }
}