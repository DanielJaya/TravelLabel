package com.example.travellabel.di

import android.content.Context
import com.example.travellabel.Data.api.Api
import com.example.travellabel.Data.pref.UserPreference
import com.example.travellabel.Data.pref.UserRepository
import com.example.travellabel.Data.pref.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiservice = Api.getApiService(user.token)
        return UserRepository.getInstance(apiservice,pref)
    }
}