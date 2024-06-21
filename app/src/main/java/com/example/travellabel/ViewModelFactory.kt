package com.example.travellabel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.travellabel.Data.api.ApiService
import com.example.travellabel.Data.pref.UserPreference
import com.example.travellabel.Data.pref.UserRepository
import com.example.travellabel.Data.pref.dataStore
import com.example.travellabel.View.AddLocation.AddLocationViewModel
import com.example.travellabel.View.AddReply.AddReplyViewModel
import com.example.travellabel.View.CreateForum.CreateForumViewModel
import com.example.travellabel.View.EditProfile.EditProfileViewModel
import com.example.travellabel.View.Forum.ForumViewModel
import com.example.travellabel.View.Fragment.DescLocationFragment.DescLocationViewModel
import com.example.travellabel.View.Login.LoginViewModel
import com.example.travellabel.View.Main.MainViewModel
import com.example.travellabel.View.Map.MapsViewModel
import com.example.travellabel.View.Profile.ProfileViewModel
import com.example.travellabel.View.Reply.ReplyViewModel
import com.example.travellabel.View.Signup.SignupViewModel
import com.example.travellabel.di.injection

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val repository: UserRepository, private val apiService: ApiService) : ViewModelProvider.NewInstanceFactory() {
    override fun <Type : ViewModel> create(modelClass: Class<Type>): Type {
        val viewModel = when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(repository)
            modelClass.isAssignableFrom(SignupViewModel::class.java) -> SignupViewModel(repository)
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(repository)
            modelClass.isAssignableFrom(MapsViewModel::class.java) -> MapsViewModel(repository)
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> ProfileViewModel(repository)
            modelClass.isAssignableFrom(EditProfileViewModel::class.java) -> EditProfileViewModel(repository)
            modelClass.isAssignableFrom(AddLocationViewModel::class.java) -> AddLocationViewModel(repository)
            modelClass.isAssignableFrom(DescLocationViewModel::class.java) -> DescLocationViewModel(repository, apiService)
            modelClass.isAssignableFrom(ForumViewModel::class.java) -> ForumViewModel(repository)
            modelClass.isAssignableFrom(CreateForumViewModel::class.java) -> CreateForumViewModel(repository)
            modelClass.isAssignableFrom(ReplyViewModel::class.java) -> ReplyViewModel(repository)
            modelClass.isAssignableFrom(AddReplyViewModel::class.java) -> AddReplyViewModel(repository)
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
        @Suppress("UNCHECKED_CAST")
        return viewModel as Type
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun clearInstance() {
            UserRepository.clearInstance()
            INSTANCE = null
        }

        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: ViewModelFactory(
                    injection.provideRepository(context),
                    injection.provideApiService(context)
                ).also { INSTANCE = it }
            }
        }
    }
}