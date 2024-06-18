package com.example.travellabel.View.Login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.travellabel.Request.LoginRequest
import com.example.travellabel.Data.pref.UserModel
import com.example.travellabel.R
import com.example.travellabel.View.Main.MainActivity
import com.example.travellabel.ViewModelFactory
import com.example.travellabel.databinding.ActivityLoginBinding
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private var _binding: ActivityLoginBinding? = null
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

            submitLogin()
    }


    private fun submitLogin() {
        binding.loginButton.setOnClickListener {
            val username = binding.edLoginUsername.text.toString()
            val password = binding.edLoginPassword.text.toString()

            if (username.isEmpty()) {
                binding.usernameEditTextLayout.error = getString(R.string.errorusername)
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.passwordEditTextLayout.error = getString(R.string.errorpassword)
                return@setOnClickListener
            }
            binding.usernameEditTextLayout.error = null
            binding.passwordEditTextLayout.error = null

            // Memanggil fungsi login dari ViewModel
            viewModel.login(LoginRequest(username, password))
        }

        viewModel.loginResult.observe(this) { result ->
            val username = binding.edLoginUsername.text.toString()
            Log.e("LoginActivity", "Login result: $result")
            if (result == null || result.status.isEmpty()) {
                // Login failed, show feedback to the user
                showToast(getString(R.string.loginfailed))
            } else {
                // Login successful, save session
                showToast(getString(R.string.loginsuccess))
                saveSession(
                    UserModel(
                        username,
                        result.status,
                        result.message,
                        result.accessToken,
                        result.refreshToken ?: "", // Pastikan refreshToken tidak null
                        true
                    )
                )
            }
        }
    }

    private fun saveSession(session: UserModel) {
        lifecycleScope.launch {
            viewModel.saveSession(session)
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            ViewModelFactory.clearInstance()
            startActivity(intent)
            finish()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}