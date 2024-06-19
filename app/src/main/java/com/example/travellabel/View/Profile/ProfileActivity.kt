package com.example.travellabel.View.Profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.travellabel.R
import com.example.travellabel.View.Bookmark.BookmarkActivity
import com.example.travellabel.View.EditProfile.EditProfileActivity
import com.example.travellabel.View.Forum.ForumActivity
import com.example.travellabel.View.Login.LoginActivity
import com.example.travellabel.View.Main.MainActivity
import com.example.travellabel.View.Map.MapsActivity
import com.example.travellabel.ViewModelFactory
import com.example.travellabel.databinding.ActivityProfileBinding
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {
    private lateinit var viewModel: ProfileViewModel
    private var _binding: ActivityProfileBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        _binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory).get(ProfileViewModel::class.java)
        navbar()
        getSession()
        setViews()

    }

    private fun setupAction(username: String) {
        viewModel.getUser(username)

        viewModel.username.observe(this) {
            binding.tvUsername.text = "Username : \n${it}"
        }
        viewModel.email.observe(this) {
            binding.tvEmail.text = "Email : \n${it}"
        }

        binding.buttonEditprofile.setOnClickListener {
            val intent = Intent(this@ProfileActivity, EditProfileActivity::class.java)
            ViewModelFactory.clearInstance()
            startActivity(intent)
        }
    }

    private fun navbar() {
        val navBottom = binding.navBottom
        navBottom.selectedItemId = R.id.profile

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
                    true
                }
                else -> false
            }
        }
    }

    private fun getSession() {
        viewModel.getSession().observe(this) { user ->
            Log.d("ProfileActivity", "User session observed: $user")
            if (user.isLogin) {
                AlertDialog.Builder(this@ProfileActivity).apply {
                    setTitle("Yeah!")
                    setMessage("Halo ${user.username} ")
                    setPositiveButton("Lanjut") { _, _ ->
                        finish()
                    }
                    create()
                    show()
                }
//                token = user.token
//                binding.topAppBar.title = "Token Main : $token"
//                setupView()
                setupAction(user.username)
            }
        }
    }

    private fun setViews() {
        binding.logoutButton.setOnClickListener {
            lifecycleScope.launch {
                viewModel.logout()
                navigateToLoginActivity()
            }
        }
    }

    private fun navigateToLoginActivity(){
        startActivity(Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}