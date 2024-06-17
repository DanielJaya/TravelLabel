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
import com.example.travellabel.Response.RegisterResponse
import com.example.travellabel.View.Main.MainActivity
import com.example.travellabel.View.Signup.SignupActivity
import com.example.travellabel.ViewModelFactory
import com.example.travellabel.databinding.ActivityLoginBinding
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

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

            //submitLogin()
    }

    /*
    private fun submitLogin(){
        binding.loginButton.setOnClickListener {
            val identifier = binding.edLoginUsername.text.toString().trim()
            val password = binding.edLoginPassword.text.toString().trim()

            if (identifier.isEmpty()) {
                binding.usernameEditTextLayout.error = getString(R.string.errorusername)
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.passwordEditTextLayout.error = getString(R.string.errorpassword)
                return@setOnClickListener
            }
            binding.usernameEditTextLayout.error = null
            binding.passwordEditTextLayout.error = null

            try {
                viewModel.login(identifier, password)
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, RegisterResponse::class.java)
                errorResponse.message?.let { it1 -> showToast(it1) }
                return@setOnClickListener
            }

        }

        viewModel.loginResult.observe(this) { result ->
            Log.e("LoginActivity", "error: $result")
            if (result.status=="BAD_REQUEST") {
                // Login failed, show feedback to the user
                showToast(getString(R.string.loginfailed))
            } else {
                // Login successful, save session
                showToast(getString(R.string.loginsuccess))
                saveSession(
                    UserModel(
                        result?.accessToken.toString(),
                        result?.message.toString(),
                        result?.refreshToken.toString(),
                        result?.status.toString(),
                        true
                    )
                )
            }
        }

        binding.noAccount.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignupActivity::class.java)
            ViewModelFactory.clearInstance()
            startActivity(intent)
        }

    }

    private fun saveSession(session: UserModel) {
        lifecycleScope.launch {
            viewModel.saveSession(session)
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            ViewModelFactory.clearInstance()
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showToast(message : String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
     */
}