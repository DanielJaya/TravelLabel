package com.example.travellabel.View.Main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travellabel.Data.Adapter.LocationAdapter
import com.example.travellabel.Data.pref.Output
import com.example.travellabel.R
import com.example.travellabel.View.Bookmark.BookmarkActivity
import com.example.travellabel.View.Forum.ForumActivity
import com.example.travellabel.View.Map.MapsActivity
import com.example.travellabel.View.Profile.ProfileActivity
import com.example.travellabel.ViewModelFactory
import com.example.travellabel.View.Welcome.WelcomeActivity
import com.example.travellabel.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var token: String

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var locationAdapter: LocationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        navbar()
        getSession()
    }

    private fun setupAction() {

    }

    private fun setupView(){
        locationAdapter = LocationAdapter()
        binding.rvMain.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = locationAdapter
        }
    }

    private fun navbar() {
        val navBottom = binding.navBottom
        navBottom.selectedItemId = R.id.home

        navBottom.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    // No action needed for Home as it's already the main activity
                    true
                }
                R.id.map -> {
                    val intent = Intent(this, MapsActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.bookmark -> {
                    val intent = Intent(this, BookmarkActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.forum -> {
                    val intent = Intent(this, ForumActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.profile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    ViewModelFactory.clearInstance()
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    private fun getSession() {
        viewModel.getSession().observe(this) { user ->
            Log.d("MainActivity", "User session observed: $user")
            if (!user.isLogin) {
                navigateToWelcomeActivity()
            } else {
                viewModel.getLocation().observe(this, Observer { result ->
                    when (result) {
                        is Output.Success -> {
                            Log.d("MainActivity", "Locations fetched successfully")
                            locationAdapter.setLocations(result.value.locations)
                        }
                        is Output.Error -> {
                            Log.e("MainActivity", "Error: ${result.error}")
                        }
                        is Output.Loading -> {
                            Log.d("MainActivity", "Loading locations...")
                        }
                    }
                })
            }
        }
    }


    private fun navigateToWelcomeActivity() {
        startActivity(Intent(this, WelcomeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
        finish()
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}