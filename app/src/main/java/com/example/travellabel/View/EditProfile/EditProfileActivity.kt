package com.example.travellabel.View.EditProfile

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.travellabel.Data.pref.UserModel
import com.example.travellabel.R
import com.example.travellabel.Request.UpdateProfileRequest
import com.example.travellabel.View.Bookmark.BookmarkActivity
import com.example.travellabel.View.Forum.ForumActivity
import com.example.travellabel.View.Main.MainActivity
import com.example.travellabel.View.Map.MapsActivity
import com.example.travellabel.View.Profile.ProfileActivity
import com.example.travellabel.View.Profile.ProfileViewModel
import com.example.travellabel.ViewModelFactory
import com.example.travellabel.databinding.ActivityEditProfileBinding
import com.example.travellabel.databinding.ActivityProfileBinding

class EditProfileActivity : AppCompatActivity() {
    private lateinit var viewModel: EditProfileViewModel
    private var _binding: ActivityEditProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        _binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory).get(EditProfileViewModel::class.java)
        navbar()
        getSession()
//        setupAction()
    }

    private fun setupAction(userModel: UserModel) {

        binding.edEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.isEmpty() == true) {
                    binding.emailEditTextLayout.error = "Email tidak boleh kosong"
                }
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.edUsername.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.isEmpty() == true) {
                    binding.usernameEditTextLayout.error = "Username tidak boleh kosong"
                }
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.buttonSimpanprofile.setOnClickListener {
            val usernameUpdate = binding.edUsername.text.toString()
            val emailUpdate = binding.edEmail.text.toString()
            var usernameTeks = "Denis"
            var emailTeks = "denis@gmail.com"

            var updateProfileRequest = UpdateProfileRequest(emailUpdate, usernameUpdate)

            viewModel.updateUser(userModel.username, updateProfileRequest)

            viewModel.username.observe(this) {usernameTekss ->
                viewModel.email.observe(this) {emailTekss ->
                    val modelPengguna = UserModel(
                        usernameTekss,
                        userModel.status,
                        userModel.message,
                        userModel.accessToken,
                        userModel.refreshToken,
                        true
                    )

                    viewModel.logout()
                    viewModel.saveSession(modelPengguna)

                    AlertDialog.Builder(this@EditProfileActivity).apply {
                        setTitle("Yeah!")
                        setMessage("Halo2 ${usernameTekss} dan ${emailTekss} ")
                        setPositiveButton("Lanjut") { _, _ ->
                            val intent = Intent(this@EditProfileActivity, ProfileActivity::class.java)
                            ViewModelFactory.clearInstance()
                            startActivity(intent)
                            finish()
                        }
                        create()
                        show()
                    }

                }
            }





//            viewModel.logout()
//            viewModel.saveSession()

//            val intent = Intent(this@EditProfileActivity, ProfileActivity::class.java)
//            ViewModelFactory.clearInstance()
//            startActivity(intent)
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
                R.id.profile -> {
                    true
                }
                else -> false
            }
        }
    }

    private fun getSession() {
        viewModel.getSession().observe(this) { user ->
            Log.d("EditProfileActivity", "User session observed: $user")
            if (user.isLogin) {
//                token = user.token
//                binding.topAppBar.title = "Token Main : $token"
//                setupView()
                setupAction(user)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}