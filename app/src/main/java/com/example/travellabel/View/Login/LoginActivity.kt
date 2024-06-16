package com.example.travellabel.View.Login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.travellabel.Data.pref.UserModel
import com.example.travellabel.R
import com.example.travellabel.Response.LoginResponse
import com.example.travellabel.Response.RegisterResponse
import com.example.travellabel.View.Main.MainActivity
import com.example.travellabel.View.Signup.SignupActivity
import com.example.travellabel.ViewModelFactory
import com.example.travellabel.databinding.ActivityLoginBinding
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        submitLogin()
    }

    private fun submitLogin() {
        binding.loginButton.setOnClickListener {
            val identifier = binding.edLoginUsername.text.toString().trim()
            val password = binding.edLoginPassword.text.toString().trim()

            if (identifier.isEmpty()) {
                binding.usernameEditTextLayout.error = getString(R.string.errorusername)
            } else if (password.isEmpty()) {
                binding.passwordEditTextLayout.error = getString(R.string.errorpassword)
            } else {
                binding.usernameEditTextLayout.error = null
                binding.passwordEditTextLayout.error = null

                lifecycleScope.launch {
                    try {
                        viewModel.login(identifier, password,
                            onResult = { response ->
                                showToast(response?.message)
                                if (response?.status == "OK") {
                                    // Save session
                                    val user = UserModel(
                                        token = response.accessToken,
                                        name = identifier,
                                        userId = "",
                                        status = response.status,
                                        isLogin = true
                                    )
                                    lifecycleScope.launch {
                                        viewModel.saveSession(user)
                                    }
                                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                            },
                            onError = { errorMessage ->
                                showToast(errorMessage)
                            }
                        )
                    } catch (e: HttpException) {
                        val errorBody = e.response()?.errorBody()?.string()
                        val errorResponse = Gson().fromJson(errorBody, LoginResponse::class.java)
                        showToast(errorResponse.message)
                    }
                }
            }
        }
    }

    private fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}