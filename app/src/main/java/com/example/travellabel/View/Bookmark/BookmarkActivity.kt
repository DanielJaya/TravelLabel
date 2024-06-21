package com.example.travellabel.View.Bookmark

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travellabel.Data.Adapter.BookmarkAdapter
import com.example.travellabel.Data.api.Api
import com.example.travellabel.Data.api.ApiService
import com.example.travellabel.Data.pref.UserPreference
import com.example.travellabel.Data.pref.dataStore
import com.example.travellabel.R
import com.example.travellabel.Response.BookmarkResponse
import com.example.travellabel.View.Forum.ForumActivity
import com.example.travellabel.View.Main.MainActivity
import com.example.travellabel.View.Map.MapsActivity
import com.example.travellabel.View.Profile.ProfileActivity
import com.example.travellabel.databinding.ActivityBookmarkBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookmarkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookmarkBinding
    private lateinit var bookmarkAdapter: BookmarkAdapter
    private lateinit var userPreference: UserPreference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBookmarkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navbar()

        userPreference = UserPreference.getInstance(dataStore)

        binding.rvBookmark.layoutManager = LinearLayoutManager(this)
        binding.rvBookmark.setHasFixedSize(true) // Tambahkan ini

        lifecycleScope.launch {
            val token = getToken()
            bookmark(token)
        }
    }

    private fun navbar(){
        val navBottom = findViewById<BottomNavigationView>(R.id.navBottom)
        navBottom.selectedItemId = R.id.bookmark

        navBottom.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.map -> {
                    val intent = Intent(this, MapsActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.bookmark -> {

                    true
                }
                R.id.profile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    private suspend fun getToken(): String {
        val userModel = userPreference.getSession().first()
        return userModel.accessToken
    }

    private fun bookmark(token: String) {
        val apiService = Api.getApiService(token)
        apiService.getBookmark().enqueue(object : Callback<BookmarkResponse> {
            override fun onResponse(call: Call<BookmarkResponse>, response: Response<BookmarkResponse>) {
                if (response.isSuccessful) {
                    val bookmarks = response.body()?.locations ?: emptyList()
                    Log.d("BookmarkActivity", "Bookmarks received: $bookmarks")
                    bookmarkAdapter = BookmarkAdapter(bookmarks)
                    binding.rvBookmark.adapter = bookmarkAdapter
                    bookmarkAdapter.notifyDataSetChanged() // Tambahkan ini
                } else {
                    Log.e("BookmarkActivity", "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<BookmarkResponse>, t: Throwable) {
                Log.e("BookmarkActivity", "Error: ${t.message}")
            }
        })
    }
}